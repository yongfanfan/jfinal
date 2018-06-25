package com.haitai.haitaitv.module.back.common;

import com.alibaba.fastjson.JSON;
import com.haitai.haitaitv.common.entity.StbConfig;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.entity.base.OperatorIdAdapter;
import com.haitai.haitaitv.common.repository.StbConfigDao;
import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.jfinal.base.BaseController;
import com.haitai.haitaitv.component.util.StrUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.sql.core.engine.PageQuery;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * 封装所有后端控制器的通用属性与操作
 *
 * @author liuzhou
 *         create at 2017-03-26 20:30
 */
public abstract class BaseBackController extends BaseController {

    public SysUser getSessionUser() {
        return getSessionAttr(SessionConsts.USER);
    }

    /**
     * 获取分页查询对象
     * 要求请求中包含pageNumber、pageSize、orderField、orderDirection四个可选字段
     */
    public <T> PageQuery<T> getPageQuery(Object paras) {
        PageQuery<T> pageQuery = new PageQuery<T>(1, paras);
        pageQuery.setPageSize(getParaToInt("pageSize", ConfigConsts.DEFAULT_PAGE_SIZE));
        String orderBy = null;
        String orderField = getPara("orderField");
        String orderDirection = getPara("orderDirection");
        if (StrUtil.isNotEmpty(orderField)) {
            setAttr("orderField", orderField);
            if (StrUtil.isNotEmpty(orderDirection)) {
                orderBy = orderField + " " + orderDirection;
                setAttr("orderDirection", orderDirection);
            } else {
                orderBy = orderField + " asc";
                setAttr("orderDirection", "asc");
            }
        }
        pageQuery.setOrderBy(orderBy);
        return pageQuery;
    }

    /**
     * 无视businessType属性而构造出的渠道父子map结构（可根据nickId过滤）
     *
     * @param excludeSameNick 若为true，则子渠道列表里将过滤掉与父渠道nickId相同的
     * @return map，键是父渠道，值是子渠道列表
     */
    protected Map<StbConfig, List<StbConfig>> getOperatorListMap(boolean excludeSameNick) {
        String userOperatorId = getSessionUser().getOperatorId();
        List<StbConfig> allConfigs = sm.getMapper(StbConfigDao.class).all();
        Map<StbConfig, List<StbConfig>> configListMap = new LinkedHashMap<>();
        Predicate<StbConfig> predicate;
        if (ConfigConsts.SERVER_TYPE != 1 && ConfigConsts.COMMERCE_OPERATOR_ID.equals(userOperatorId)) {
            // 公网服务器且是公网运营人员
            predicate = config -> StrUtil.isEmpty(config.getParentOperatorId());
        } else {
            predicate = config -> userOperatorId.equals(config.getOperatorId());
        }
        allConfigs.stream().filter(predicate).forEachOrdered(config -> {
            // 使用LinkedList提高插入性能
            List<StbConfig> children = new LinkedList<>();
            configListMap.put(config, children);
            String operatorId = config.getOperatorId();
            String nickId = config.getNickId();
            for (StbConfig child : allConfigs) {
                if (operatorId.equals(child.getParentOperatorId()) &&
                        // 要么不需要过滤，要么nickId不相同
                        (!excludeSameNick || !nickId.equals(child.getNickId()))) {
                    children.add(child);
                }
            }
        });
        return configListMap;
    }

    /**
     * 无视businessType属性而构造出的渠道list结构（可根据nickId过滤）
     *
     * @param excludeSameNick 若为true，则子渠道列表里将过滤掉与父渠道nickId相同的
     * @return 渠道列表
     */
    protected List<StbConfig> getOperatorList(boolean excludeSameNick) {
        BiConsumer<List<StbConfig>, Map.Entry<StbConfig, List<StbConfig>>> accumulator =
                (list, entry) -> {
                    list.add(entry.getKey());
                    list.addAll(entry.getValue());
                };
        return getOperatorListMap(excludeSameNick).entrySet().stream()
                // 三个参数为别是：new实例的方法，添加元素到实例的方法，整合两个实例的方法
                .collect(ArrayList::new, accumulator, List::addAll);
    }

    /**
     * 获取common中的渠道选择信息，再将operatorId和operatorIds设置进bean
     * 该方法将会调用到limitOperator()
     * total时operatorId与operatorIds均空，xxxTotal时operatorId与operatorIds均非空，单独一个渠道时仅operatorId非空
     *
     * @param attr 用于sql查询的bean，需要实现OperatorIdAdapter接口
     * @return 最终所选择的渠道号（total或xxxTotal或单独渠道号)
     * @see BaseBackController#limitOperator()
     */
    protected String setLimitOrperatorFromCommon(OperatorIdAdapter attr) {
        return setLimitOrperatorFromCommon(attr, false);
    }

    /**
     * 获取common中的渠道选择信息，再将operatorId和operatorIds设置进bean
     * 该方法将会调用到limitOperator(boolean)
     * total时operatorId与operatorIds均空，xxxTotal时operatorId与operatorIds均非空，单独一个渠道时仅operatorId非空
     *
     * @param attr               用于sql查询的bean，需要实现OperatorIdAdapter接口
     * @param showPublicChildren 是否展示公众子渠道
     * @return 最终所选择的渠道号（total或xxxTotal或单独渠道号)
     * @see BaseBackController#limitOperator(boolean)
     */
    protected String setLimitOrperatorFromCommon(OperatorIdAdapter attr, boolean showPublicChildren) {
        CommonDTO common = getBean(CommonDTO.class, "common");
        setAttr("common", common);
        // 非公网或非888888的用户将只能看到自己的数据
        String operatorId = limitOperator(showPublicChildren);
        if (common.getOperatorId2() != null) {
            operatorId = common.getOperatorId2();
        } else if (common.getOperatorId() != null) {
            operatorId = common.getOperatorId();
        }
        String result = operatorId;
        List<String> operatorIds = null;
        if ("total".equals(operatorId)) {
            operatorId = null;
        } else if (operatorId.endsWith("Total")) {
            operatorIds = getXxxOperatorIds(operatorId);
        }
        attr.setOperatorId(operatorId);
        attr.set("operatorIds", operatorIds);
        return result;
    }

    /**
     * 渠道下拉框将放在request的configSelect属性（隐藏公众子渠道）
     * 子渠道的businessType属性将影响它展示在一级、二级甚至不展示
     *
     * @return 下拉框的默认值
     */
    protected String limitOperator() {
        return limitOperator(false);
    }

    /**
     * 渠道下拉框将放在request的configSelect属性
     * 子渠道的businessType属性将影响它展示在一级、二级甚至不展示
     * 需要判断用户权限来决定是否展示渠道
     *
     * @param showPublicChildren 是否展示公众子渠道
     * @return 下拉框的默认值
     */
    protected String limitOperator(boolean showPublicChildren) {
        String userOperatorId = getSessionUser().getOperatorId();
        List<StbConfig> allConfigs = sm.getMapper(StbConfigDao.class).all();
        Map<StbConfig, List<StbConfig>> configSelect = new LinkedHashMap<>();
        // 子渠道商通常展示在二级下拉框
        setAttr("configSelect", configSelect);

        Subject subject = SecurityUtils.getSubject();
        // 检查是否有权限，优先查看是否有所有权限，用户默认拥有自己渠道的权限
        boolean allPermitted = subject.isPermitted("operator:view");
        Predicate<StbConfig> temp = config -> allPermitted;
        temp = temp.or(config -> userOperatorId.equals(config.getOperatorId()));
        Predicate<StbConfig> permissionPredicate = temp
                .or(config -> subject.isPermitted("operator:" + config.getOperatorId() + ":view"));

        // 对一级下拉框进行过滤
        Predicate<StbConfig> predicateOne;
        if (allPermitted) {
            if (ConfigConsts.SERVER_TYPE != 1) {
                // 拥有所有渠道权限且不是内网，要展示合计
                StbConfig total = new StbConfig();
                total.setOperatorName("合计");
                total.setOperatorId("total");
                configSelect.put(total, new ArrayList<>());
            }
            // 无父渠道的，或与父渠道无商业合作的，将作为一级下拉框
            predicateOne = config -> StrUtil.isEmpty(config.getParentOperatorId()) || config.getBusinessType() != 1;
        } else {
            // 用户所在的渠道也将作为一级下拉框
            predicateOne = config -> userOperatorId.equals(config.getOperatorId())
                    || StrUtil.isEmpty(config.getParentOperatorId()) || config.getBusinessType() != 1;
            // 未拥有所有渠道权限，所以需要对每个渠道单独检查权限
            predicateOne = predicateOne.and(permissionPredicate);
        }

        allConfigs.stream().filter(predicateOne)
                // 遍历"合计"以外的一级下拉框，给它们分配各自的二级下拉框
                .forEachOrdered(config -> {
                    // 使用LinkedList提高插入性能
                    List<StbConfig> children = new LinkedList<>();
                    configSelect.put(config, children);
                    String operatorId = config.getOperatorId();
                    if (!showPublicChildren
                            && ConfigConsts.SERVER_TYPE != 1
                            && ConfigConsts.COMMERCE_OPERATOR_ID.equals(operatorId)) {
                        // 当showPublicChildren为false时，不展示子渠道
                        // （检查了不是内网，且渠道号是服务器的渠道号，所以实际上就跳过了公众子渠道）
                        return;
                    }
                    // 对二级下拉框进行过滤，展示与父渠道有商业合作的，并判断权限
                    Predicate<StbConfig> predicateTow = child -> child.getBusinessType() == 1
                            && operatorId.equals(child.getParentOperatorId());
                    predicateTow = predicateTow.and(permissionPredicate);
                    allConfigs.stream().filter(predicateTow).forEach(children::add);
                    // 当二级下拉框非空时，要在开头加上渠道合计和父渠道
                    if (children.size() > 0) {
                        StbConfig sum = new StbConfig();
                        sum.setOperatorName("渠道合计");
                        sum.setOperatorId(operatorId + "Total");
                        children.add(0, sum);
                        children.add(1, config);
                    }
                });
        // 返回下拉框的默认值（找到一级下拉框的第一个，若它有二级，就返二级第一个，否则返回它）
        Iterator<Map.Entry<StbConfig, List<StbConfig>>> iterator = configSelect.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<StbConfig, List<StbConfig>> entry = iterator.next();
            List<StbConfig> configs = entry.getValue();
            if (configs.size() > 0) {
                return configs.get(0).getOperatorId();
            }
            return entry.getKey().getOperatorId();
        }
        return null;
    }

    /**
     * 渠道下拉框将放在request的configSelect属性
     * 子渠道的businessType属性将影响它展示在一级、二级甚至不展示
     *
     * @param showPublicChildren 是否展示公众子渠道
     * @return 下拉框的默认值
     */
    // 非888888的用户将只能看到自己的运营数据
    // 新增参数showPublicChildren，默认为true，展品/商品管理可传false，false情形将不展示公众子渠道
    @Deprecated
    protected String limitOperatorOld(boolean showPublicChildren) {
        String userOperatorId = getSessionUser().getOperatorId();
        List<StbConfig> allConfigs = sm.getMapper(StbConfigDao.class).all();
        Map<StbConfig, List<StbConfig>> configSelect = new LinkedHashMap<>();
        // 子渠道商通常展示在二级下拉框
        setAttr("configSelect", configSelect);
        Predicate<StbConfig> predicate;
        // 现在使用serverType判断是否公网，是公网的话，再用服务器渠道号判断是否公网用户
        if (ConfigConsts.SERVER_TYPE != 1 && ConfigConsts.COMMERCE_OPERATOR_ID.equals(userOperatorId)) {
            // 仅当在海苔公网服务器上显示所有渠道的合计数据
            StbConfig total = new StbConfig();
            total.setOperatorName("合计");
            total.setOperatorId("total");
            configSelect.put(total, new ArrayList<>());
            // 无父渠道的，或与父渠道无商业合作的，将作为一级下拉框
            predicate = config -> StrUtil.isEmpty(config.getParentOperatorId()) || config.getBusinessType() != 1;
        } else {
            // 该渠道将作为一级下拉框
            predicate = config -> userOperatorId.equals(config.getOperatorId())
                    // 该渠道的子渠道且与无合作但展示数据的，也作为一级下拉框
                    || (userOperatorId.equals(config.getParentOperatorId()) && config.getBusinessType() == 3);
        }
        /*if ("888888".equals(userOperatorId)) {
            // 登录用户为海苔的
            if ("888888".equals(ConfigConsts.COMMERCE_OPERATOR_ID)) {
                // 仅当在海苔公网服务器上显示所有渠道的合计数据
                StbConfig total = new StbConfig();
                total.setOperatorName("合计");
                total.setOperatorId("total");
                configSelect.put(total, new ArrayList<>());
            }
            // 无父渠道的，或与父渠道无商业合作的，将作为一级下拉框
            predicate = config -> StrUtil.isEmpty(config.getParentOperaid()) || config.getIsBusiness() != 1;
        } else {
            // 该渠道，或为该渠道的子渠道且与无合作但展示数据的，将作为一级下拉框
            predicate = config -> StrUtil.isEmpty(config.getParentOperaid()) || config.getIsBusiness() == 3;
        }*/
        allConfigs.stream().filter(predicate)
                // 遍历"合计"以外的一级下拉框，给它们分配各自的二级下拉框
                .forEachOrdered(config -> {
                    // 使用LinkedList提高插入性能
                    List<StbConfig> children = new LinkedList<>();
                    configSelect.put(config, children);
                    String operatorId = config.getOperatorId();
                    // if (!showPublicChildren && "888888".equals(operatorId)) { 公众子渠道判断修改
                    if (!showPublicChildren
                            && ConfigConsts.SERVER_TYPE != 1
                            && ConfigConsts.COMMERCE_OPERATOR_ID.equals(operatorId)) {
                        // 当showPublicChildren为false时，不展示公众子渠道
                        return;
                    }
                    for (StbConfig child : allConfigs) {
                        // 二级下拉框里展示与父渠道有商业合作的
                        if (child.getBusinessType() == 1 && operatorId.equals(child.getParentOperatorId())) {
                            children.add(child);
                        }
                    }
                    // 当二级下拉框非空时，要在开头加上渠道合计和父渠道
                    if (children.size() > 0) {
                        StbConfig sum = new StbConfig();
                        sum.setOperatorName("渠道合计");
                        sum.setOperatorId(operatorId + "Total");
                        children.add(0, sum);
                        children.add(1, config);
                    }
                });
        // 返回下拉框的默认值（找到一级下拉框的第一个，若它有二级，就返二级第一个，否则返回它）
        Iterator<Map.Entry<StbConfig, List<StbConfig>>> iterator = configSelect.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<StbConfig, List<StbConfig>> entry = iterator.next();
            List<StbConfig> configs = entry.getValue();
            if (configs.size() > 0) {
                return configs.get(0).getOperatorId();
            }
            return entry.getKey().getOperatorId();
        }
        return null;
    }

    protected List<String> getXxxOperatorIds(String operatorId) {
        operatorId = operatorId.substring(0, operatorId.length() - 5);
        List<String> operatorIds = sm.getMapper(StbConfigDao.class).listChildrenOperatorId(operatorId);
        operatorIds.add(operatorId);
        return operatorIds;
    }



    /**
     * 导出excel通用方法，用于action的末尾
     */
    protected void renderExcel(Workbook workbook, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            LOG.error("导出时将文件名转码失败");
        }
        getResponse().addHeader("Content-Disposition", "attachment; filename=" + fileName);
        getResponse().addHeader("Content-Type", "application/octet-stream");
        try {
            workbook.write(getResponse().getOutputStream());
        } catch (IOException e) {
            LOG.error("导出报表出错", e);
        }
        renderNull();
    }

    /**
     * 以下是通用解释，并不完全适用所有情形
     * （如回调dialogAjaxDone在处理callbackType时，只处理closeCurrent的情形，另外也可自定义回调）
     * 具体需参考dwz.ajax.js的源码
     *
     * @param statusCode   状态码，200成功，300失败，301超时，601部分成功（弹warn框，需使用myNavTabAjaxDone回调）
     * @param message      提示语
     * @param navTabId     刷新指定id的navTab，若为空，则刷新当前激活的navTab，可以传入不存在的navTabId来达到不刷新任何navTab的效果
     * @param rel          暂未使用过，按dwz.ajax.js的解释为“可选 用于局部刷新div id号”
     * @param callbackType 在dialogAjaxDone和navTabAjaxDone中，closeCurrent将关闭当前dialog或navTab，
     *                     在navTabAjaxDone中，forward会将当前navTab跳转到forwardUrl，
     *                     在navTabAjaxDone中，forwardConfirm会弹出确认框，要么跳转，要么关闭当前或指定的navTab
     *                     在navTabAjaxDone中，非以上三种时会将当前navTab表单重置
     * @param forwardUrl   跳转链接，参考callbackType的解释
     */
    private void dwzRender(int statusCode, String message, String navTabId, String rel, String callbackType, String forwardUrl) {
        DwzDTO dto = new DwzDTO(statusCode, message, navTabId, rel, callbackType, forwardUrl);
        // 解决ie（10以下）遇到response.setContentType("application/json;charset=UTF-8")的返回数据时下载文件的问题
        renderText(JSON.toJSONString(dto));
    }

    protected void dwzFail(String message) {
        dwzRender(300, message, null, null, null, null);
    }

    protected void dwz601(String message) {
        dwzRender(601, message, null, null, "closeCurrent", null);
    }

    protected void dwzSuccess(String message) {
        dwzRender(200, message, null, null, "closeCurrent", null);
    }

    protected void dwzSuccess(String message, String navTabId) {
        dwzRender(200, message, navTabId, null, "closeCurrent", null);
    }

    protected void dwzSuccess(String message, String navTabId, String forwardUrl) {
        dwzRender(200, message, navTabId, null, "closeCurrent", forwardUrl);
    }

    protected void dwzSuccessNoClose(String message) {
        dwzRender(200, message, null, null, null, null);
    }

    protected void dwzSuccessNoClose(String message, String navTabId) {
        dwzRender(200, message, navTabId, null, null, null);
    }

    protected void dwzSuccessForwardUrl(String message, String navTabId, String forwardUrl) {
        dwzRender(200, message, navTabId, null, "forward", forwardUrl);
    }

    protected void dwzTimeout() {
        dwzRender(301, "登录超时，请重新登录", null, null, null, null);
    }

    protected void dwz(DwzDTO dto) {
        renderText(JSON.toJSONString(dto));
    }
}

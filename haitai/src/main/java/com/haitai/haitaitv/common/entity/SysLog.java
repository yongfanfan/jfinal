package com.haitai.haitaitv.common.entity;

import com.haitai.haitaitv.common.repository.SysDictDetailDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 
* gen by beetlsql 2017-03-23
*/
public class SysLog extends org.beetl.sql.core.TailBean {

    private final static Logger LOG = LogManager.getLogger(SysLog.class);
    /**
     * 表中文转换
     */
    private static final Map<String, String> tableMap = new HashMap<>();

    public static void init() {
        LOG.info("####日志配置初始化......");
        tableMap.clear();
        SysDictDetailDao dao = JFinalBeetlSql.dao().getMapper(SysDictDetailDao.class);
        SysDictDetail template = new SysDictDetail();
        template.setDictType("systemLog");
        List<SysDictDetail> list = dao.template(template);
        for (SysDictDetail detail : list) {
            tableMap.put(detail.getDetailName(), detail.getDetailCode());
        }
    }

    /**
     * 获取表中文备注
     */
    public static String getTableRemark(String tableName) {
        return tableMap.get(tableName);
    }

    public static final Integer TYPE_MODEL = 1;
    public static final Integer TYPE_SYSTEM = 2;
    public static final String MODEL_SAVE = "添加";
    public static final String MODEL_UPDATE = "更新";
    public static final String MODEL_DELETE = "删除";
    public static final String SYSTEM_LOGIN = "登入";
    public static final String SYSTEM_LOGOUT = "登出";

    private Integer id;
    //类型
    private Integer logType;
    //操作对象
    private String operObject;
    //操作表
    private String operTable;
    //操作主键
    private Integer operId;
    //操作类型
    private String operType;
    //操作备注
    private String operRemark;
    //创建时间
    private LocalDateTime createTime;
    //创建者
    private Integer createId;

    public SysLog() {
    }

    public SysLog(Integer id, Integer logType, String operObject, String operTable, Integer operId, String operType,
                  String operRemark, LocalDateTime createTime, Integer createId) {
        this.id = id;
        this.logType = logType;
        this.operObject = operObject;
        this.operTable = operTable;
        this.operId = operId;
        this.operType = operType;
        this.operRemark = operRemark;
        this.createTime = createTime;
        this.createId = createId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getOperObject() {
        return operObject;
    }

    public void setOperObject(String operObject) {
        this.operObject = operObject;
    }

    public String getOperTable() {
        return operTable;
    }

    public void setOperTable(String operTable) {
        this.operTable = operTable;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperRemark() {
        return operRemark;
    }

    public void setOperRemark(String operRemark) {
        this.operRemark = operRemark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }
}

package com.haitai.haitaitv.module.back.system.user;

import com.haitai.haitaitv.common.entity.SysDepartment;
import com.haitai.haitaitv.common.entity.SysMenu;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysDepartmentDao;
import com.haitai.haitaitv.common.repository.SysMenuDao;
import com.haitai.haitaitv.common.repository.SysUserDao;
import com.haitai.haitaitv.common.repository.SysUserRoleDao;
import com.haitai.haitaitv.component.jfinal.base.BaseService;

import java.util.*;

public class UserService extends BaseService {

    public static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    /**
     * 通过用户名查找用户
     */
    public SysUser findByUsername(String username) {
        SysUser template = new SysUser();
        template.setUsername(username);
        return sm.getMapper(SysUserDao.class).templateOne(template);
    }

    /**
     * 通过用户名和密码查找用户
     */
    public SysUser findByUsernameAndPassword(String username, String password) {
        SysUser template = new SysUser();
        template.setUsername(username);
        template.setPassword(password);
        return sm.getMapper(SysUserDao.class).templateOne(template);
    }

    public String getOperatorId(SysUser user) {
        SysDepartment department = sm.getMapper(SysDepartmentDao.class).single(user.getDepartmentId());
        return department == null ? "" : department.getOperatorId();
    }

    /**
     * 获取用户拥有的菜单，键为一级菜单，值为二级菜单列表
     */
    public Map<SysMenu, List<SysMenu>> getMenuMap(SysUser user) {
        if (user == null || user.getType() >= 3) {
            return Collections.emptyMap();
        }
        SysMenuDao dao = sm.getMapper(SysMenuDao.class);
        List<SysMenu> menus;
        if (user.getType() == 1) {
            // 管理员拥有所有菜单
            SysMenu template = new SysMenu();
            template.setStatus(1);
            template.setType(1);
            menus = dao.template(template);
        } else {
            // 查出角色集合
            List<Integer> roleids = sm.getMapper(SysUserRoleDao.class).listRoleId(user.getId());
            if (roleids.isEmpty()) {
                menus = Collections.emptyList();
            } else {
                // 根据角色集合查出菜单集合
                menus = dao.listByTypeAndRoleIds(1, roleids);
            }
        }
        return getSysMenuListMap(menus);
    }

    public Map<SysMenu, List<SysMenu>> getSysMenuListMap(List<SysMenu> menus) {
        Map<SysMenu, List<SysMenu>> menuMap = new LinkedHashMap<>();
        // 键为一级菜单id，值为二级菜单列表，用于根据父菜单id找到子菜单应存放的列表
        Map<Integer, List<SysMenu>> temp = new HashMap<>();
        // 下面两条语句本来可以合并，在foreach内部判断是否一级菜单，但考虑到可能出现子菜单的order小于父菜单的order的情况
        // 存入一级菜单
        menus.stream().filter(sysMenu -> sysMenu.getParentId() == 0).forEachOrdered(sysMenu -> {
            // noinspection MismatchedQueryAndUpdateOfCollection 这条注释是用于禁止idea进行警告的
            List<SysMenu> children = new ArrayList<>();
            menuMap.put(sysMenu, children);
            temp.put(sysMenu.getId(), children);
        });
        // 存入二级菜单
        menus.stream().filter(sysMenu -> sysMenu.getParentId() != 0).forEachOrdered(sysMenu -> {
            List<SysMenu> children = temp.get(sysMenu.getParentId());
            // 理论上不可能为空，用户拥有子菜单则一定拥有父菜单
            if (children != null) {
                children.add(sysMenu);
            }
        });
        return menuMap;
    }

    /**
     * 通过用户查找角色集合
     * 目前不直接通过角色检查权限，因此直接返回空集合（角色用于给用户授权，若需要检查角色，那么应该给角色表增加字段以存诸角色字符串）
     */
    public Set<String> findRoles(SysUser user) {
        return Collections.emptySet();
    }

    /**
     * 通过用户查找权限集合
     */
    @SuppressWarnings("unchecked")
    public Set<String> findPermissions(SysUser user) {
        if (user == null || user.getType() >= 3) {
            return Collections.EMPTY_SET;
        }
        Set<String> permissions = new HashSet<>();
        SysMenuDao dao = sm.getMapper(SysMenuDao.class);
        if (user.getType() == 1) {
            // 管理员拥有所有权限，"*"即为所有权限
            /*List<String> list = dao.listPermissionsWhenSuper();
            addSuffix(permissions, list, ":*");*/
            permissions.add("*");
            return permissions;
        }
        // 查出角色集合
        List<Integer> roleids = sm.getMapper(SysUserRoleDao.class).listRoleId(user.getId());
        if (roleids.isEmpty()) {
            return Collections.emptySet();
        }
        List<String> list = dao.listPermissionByRoleIds(1, roleids);
        addSuffix(permissions, list, ":*");
        list = dao.listPermissionByRoleIds(0, roleids);
        addSuffix(permissions, list, ":view");
        return permissions;
    }

    private void addSuffix(Set<String> set, List<String> list, String suffix) {
        for (String s : list) {
            set.add(s + suffix);
        }
    }

}

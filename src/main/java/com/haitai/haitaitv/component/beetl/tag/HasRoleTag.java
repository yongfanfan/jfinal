package com.haitai.haitaitv.component.beetl.tag;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Tag;

import java.util.Map;

/**
 * 目前仅支持验证单条role
 * 样例：
 * <#hasrole name="role字符串">
 * 标签体
 * </#hasrole>
 *
 * @author liuzhou
 *         create at 2016-12-02 13:56
 */
public class HasRoleTag extends Tag {

    @SuppressWarnings("unchecked")
    @Override
    public void render() {
        Map<String, Object> attrs = (Map<String, Object>) args[1];
        String role = (String) attrs.get("name");
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole(role)) {
            doBodyRender();
        }
    }
}

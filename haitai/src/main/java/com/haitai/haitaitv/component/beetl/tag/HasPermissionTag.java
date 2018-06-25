package com.haitai.haitaitv.component.beetl.tag;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Tag;

import java.util.Map;

/**
 * 目前仅支持验证单条permission
 * 样例：
 * <#haspermission name="permission字符串">
 * 标签体
 * </#haspermission>
 *
 * @author liuzhou
 *         create at 2016-12-05 14:12
 */
public class HasPermissionTag extends Tag {

    @SuppressWarnings("unchecked")
    @Override
    public void render() {
        Map<String, Object> attrs = (Map<String, Object>) args[1];
        String permission = (String) attrs.get("name");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted(permission)) {
            doBodyRender();
        }
    }
}

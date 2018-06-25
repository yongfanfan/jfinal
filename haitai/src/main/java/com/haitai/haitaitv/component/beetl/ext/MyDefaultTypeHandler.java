package com.haitai.haitaitv.component.beetl.ext;

import org.beetl.sql.core.mapping.type.JavaSqlTypeHandler;
import org.beetl.sql.core.mapping.type.TypeParameter;

import java.sql.SQLException;

/**
 * 扩展beetl的DefaultTypeHandler
 *
 * @author liuzhou
 *         create at 2017-06-06 9:56
 */
public class MyDefaultTypeHandler extends JavaSqlTypeHandler {

    @Override
    public Object getValue(TypeParameter typePara) throws SQLException {
        Class<?> target = typePara.getTarget();
        if (target != null) {
            return typePara.getRs().getObject(typePara.getIndex(), target);
        } else {
            return typePara.getRs().getObject(typePara.getIndex());
        }
    }

}

package com.haitai.haitaitv.component.beetl.ext;

import org.beetl.sql.core.JavaType;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.engine.SQLParameter;
import org.beetl.sql.core.kit.EnumKit;
import org.beetl.sql.core.mapping.BeanProcessor;
import org.beetl.sql.core.mapping.type.JavaSqlTypeHandler;
import org.beetl.sql.core.mapping.type.TypeParameter;
import org.beetl.sql.ext.gen.SourceGen;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// 增加对LocalDate,LocalTime,LocalDateTime的支持
public class MyBeanProcessor extends BeanProcessor {

    static {
        // 扩展JavaType
        JavaType.jdbcJavaTypes.put(Types.DATE, LocalDate.class);
        JavaType.jdbcJavaTypes.put(Types.TIME, LocalTime.class);
        JavaType.jdbcJavaTypes.put(Types.TIMESTAMP, LocalDateTime.class);
        JavaType.mapping.put(Types.DATE, "LocalDate");
        JavaType.mapping.put(Types.TIME, "LocalTime");
        JavaType.mapping.put(Types.TIMESTAMP, "LocalDateTime");
        // beetlsql默认将布尔型映射为Integer，不建议数据库用布尔型，因为is_enable字段到java中就是isEnable，getset方法会很别扭
        // 尝试过将数据库的is_enable映到成enable，但enable无法映射回is_enable，因为无法获取字段的类型
        /*JavaType.mapping.put(Types.BIT, "Boolean");
        JavaType.mapping.put(Types.BOOLEAN, "Boolean");*/
        String CR = System.getProperty("line.separator");
        // 扩展源码生成器导包部分代码
        SourceGen.srcHead += "import java.time.LocalDate;" + CR;
        SourceGen.srcHead += "import java.time.LocalTime;" + CR;
        SourceGen.srcHead += "import java.time.LocalDateTime;" + CR;
    }

    public MyBeanProcessor(SQLManager sm) {
        super(sm);
        handlers.put(LocalDate.class, new JavaSqlTypeHandler() {
            @Override
            public Object getValue(TypeParameter typePara) throws SQLException {
                return typePara.getRs().getObject(typePara.getIndex(), LocalDate.class);
            }
        });
        handlers.put(LocalDateTime.class, new JavaSqlTypeHandler() {
            @Override
            public Object getValue(TypeParameter typePara) throws SQLException {
                return typePara.getRs().getObject(typePara.getIndex(), LocalDateTime.class);
            }
        });
        // mariadb的驱动在存time时可能存为负值（应该是bug，对时区做了没有必要的判断，使用mysql的驱动无此问题），
        // 但如果带负号，在取的时候若直接使用rs.getObject(xxx, LocalDate.class)将会报错
        handlers.put(LocalTime.class, new JavaSqlTypeHandler() {
            @Override
            public Object getValue(TypeParameter typePara) throws SQLException {
                String ts = typePara.getRs().getString(typePara.getIndex());
                if (ts == null) {
                    return null;
                }
                if (ts.startsWith("-")) {
                    ts = ts.substring(1);
                }
                return LocalTime.parse(ts);
            }
        });
        // 使用自己的DefaultTypeHandler
        defaultHandler = new MyDefaultTypeHandler();
    }

    @Override
    public void setPreparedStatementPara(String sqlId, PreparedStatement ps, List<SQLParameter> objs) throws SQLException {
        for (int i = 0; i < objs.size(); i++) {
            SQLParameter para = objs.get(i);
            Object o = para.value;
            if (o == null) {
                ps.setObject(i + 1, null);
                continue;
            }
            Class c = o.getClass();
            /*if (c == LocalDate.class) {
                LocalDate d = (LocalDate) o;
                ps.setDate(i + 1, Date.valueOf(d));
                continue;
            }
            if (c == LocalTime.class) {
                LocalTime d = (LocalTime) o;
                ps.setTime(i + 1, Time.valueOf(d));
                continue;
            }
            if (c == LocalDateTime.class) {
                LocalDateTime d = (LocalDateTime) o;
                ps.setTimestamp(i + 1, Timestamp.valueOf(d));
                continue;
            }*/
            // 兼容性修改：oralce 驱动 不识别util.Date
            if (dbType == DBStyle.DB_ORACLE || dbType == DBStyle.DB_POSTGRES || dbType == DBStyle.DB_DB2) {
                if (c == java.util.Date.class) {
                    o = new Timestamp(((java.util.Date) o).getTime());
                }
            }
            if (Enum.class.isAssignableFrom(c)) {
                o = EnumKit.getValueByEnum(o);
            }
            //clob or text
            if (c == char[].class) {
                //noinspection ConstantConditions
                o = new String((char[]) o);
            }
            int jdbcType = para.getJdbcType();
            if (jdbcType == 0) {
                ps.setObject(i + 1, o);
            } else {
                //通常一些特殊的处理
                throw new UnsupportedOperationException(jdbcType + ",默认处理器并未处理此jdbc类型");
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> toBeanList(String sqlId, ResultSet rs, Class<T> type) throws SQLException {
        if (type == LocalDateTime.class) {
            List<Timestamp> results = new ArrayList<>();
            while (rs.next()) {
                Object result = toBaseType(sqlId, Timestamp.class, rs);
                results.add((Timestamp) result);
            }
            List<LocalDateTime> list = new ArrayList<>(results.size());
            results.forEach(item -> list.add(item.toLocalDateTime()));
            return (List<T>) list;
        }
        if (type == LocalDate.class) {
            List<Date> results = new ArrayList<>();
            while (rs.next()) {
                Object result = toBaseType(sqlId, Date.class, rs);
                results.add((Date) result);
            }
            List<LocalDate> list = new ArrayList<>(results.size());
            results.forEach(item -> list.add(item.toLocalDate()));
            return (List<T>) list;
        }
        if (type == LocalTime.class) {
            List<Time> results = new ArrayList<>();
            while (rs.next()) {
                Object result = toBaseType(sqlId, Time.class, rs);
                results.add((Time) result);
            }
            List<LocalTime> list = new ArrayList<>(results.size());
            results.forEach(item -> list.add(item.toLocalTime()));
            return (List<T>) list;
        }
        return super.toBeanList(sqlId, rs, type);
    }
}
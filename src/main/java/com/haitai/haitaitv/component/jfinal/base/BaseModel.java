package com.haitai.haitaitv.component.jfinal.base;

import com.haitai.haitaitv.component.jfinal.ext.AbstractModel;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

import java.util.List;

public abstract class BaseModel<M extends Model<M>> extends AbstractModel<M> {

    public Table getTable() {
        return TableMapping.me().getTable(getClass());
    }

    /**
     * @param paginator       分页器
     * @param select          the select part of the sql statement
     * @param sqlExceptSelect the sql statement excluded select part
     * @param paras           the parameters of sql
     */
    public Page<M> paginate(Paginator paginator, String select, String sqlExceptSelect, Object... paras) {
        return paginate(paginator, null, select, sqlExceptSelect, paras);
    }

    /**
     * @param paginator       分页器
     * @param isGroupBySql    指定分页 sql 最外层以是否含有 group by 语句
     * @param select          the select part of the sql statement
     * @param sqlExceptSelect the sql statement excluded select part
     * @param paras           the parameters of sql
     */
    public Page<M> paginate(Paginator paginator, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return paginate(paginator.getPageNumber(), paginator.getPageSize(), isGroupBySql, select, sqlExceptSelect, paras);
    }

    /**
     * @param where an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return the list of Model
     */
    public List<M> findByWhere(String where, Object... paras) {
        return findByWhereAndColumns(where, "*", paras);
    }

    /**
     * @param where   an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param columns 列名，用逗号分隔
     * @param paras   the parameters of sql
     * @return the list of Model
     */
    public List<M> findByWhereAndColumns(String where, String columns, Object... paras) {
        String sql = " select " + columns + " from " + getTable().getName() + " " + where;
        return find(sql, paras);
    }

    /**
     * Find first model. I recommend add "limit 1" in your sql.
     *
     * @param where an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return Model
     */
    public M findFirstByWhere(String where, Object... paras) {
        return findFirstByWhereAndColumns(where, "*", paras);
    }

    /**
     * Find first model. I recommend add "limit 1" in your sql.
     *
     * @param where   an SQL statement that may contain one or more '?' IN parameter  placeholders
     * @param columns 列名，用逗号分隔
     * @param paras   the parameters of sql
     * @return Model
     */
    public M findFirstByWhereAndColumns(String where, String columns, Object... paras) {
        String sql = " select " + columns + " from " + getTable().getName() + " " + where;
        return findFirst(sql, paras);
    }

}

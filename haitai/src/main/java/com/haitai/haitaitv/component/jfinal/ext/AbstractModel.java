package com.haitai.haitaitv.component.jfinal.ext;

import com.jfinal.plugin.activerecord.Model;

import java.math.BigDecimal;

/**
 * 扩展jfinal的Model
 */
public abstract class AbstractModel<M extends Model<M>> extends Model<M> {

    @Override
    public Integer getInt(String attr) {
        Object obj = get(attr);
        if (obj == null) {
            return null;
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        } else {
            try {
                return Integer.parseInt(obj.toString());
            } catch (Exception e) {
                throw new RuntimeException("Object can not cast to Integer : " + attr);
            }
        }
    }

}

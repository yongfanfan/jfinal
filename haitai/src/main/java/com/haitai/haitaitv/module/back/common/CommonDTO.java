package com.haitai.haitaitv.module.back.common;

/**
 * 通用前端表单属性DTO
 *
 * @author liuzhou
 *         create at 2017-03-30 16:35
 */
public class CommonDTO implements AfterSetterAdapter {

    private String operatorId;
    private String operatorId2;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorId2() {
        return operatorId2;
    }

    public void setOperatorId2(String operatorId2) {
        this.operatorId2 = operatorId2;
    }

}

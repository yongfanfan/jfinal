package com.haitai.haitaitv.module.back.admin.home;


import java.time.LocalDate;


/**
 * 承载listOrSum方法的结果
 *
 * @author liuzhou
 *         create at 2017-05-19 11:36
 * @see TbDataResultDao#listOrSum(java.util.List, java.lang.String, java.time.LocalDate, java.time.LocalDate)
 */
public class ListOrSumResultDTO {

    private LocalDate date;
    private Integer newUser;
    private Integer dau;
    private Integer mainPv;
    private Integer orderPv;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNewUser() {
        return newUser;
    }

    public void setNewUser(Integer newUser) {
        this.newUser = newUser;
    }

    public Integer getDau() {
        return dau;
    }

    public void setDau(Integer dau) {
        this.dau = dau;
    }

    public Integer getMainPv() {
        return mainPv;
    }

    public void setMainPv(Integer mainPv) {
        this.mainPv = mainPv;
    }

    public Integer getOrderPv() {
        return orderPv;
    }

    public void setOrderPv(Integer orderPv) {
        this.orderPv = orderPv;
    }
}

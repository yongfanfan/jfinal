package com.haitai.haitaitv.component.jfinal.base;

import com.haitai.haitaitv.component.constant.ConfigConsts;

/**
 * 分页器，用于封装分页请求
 */
public class Paginator {

    private int pageNumber;
    private int pageSize;

    public Paginator() {
        this.pageNumber = 1;
        this.pageSize = ConfigConsts.DEFAULT_PAGE_SIZE;
    }

    public Paginator(int pageNumber, int pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = ConfigConsts.DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

}

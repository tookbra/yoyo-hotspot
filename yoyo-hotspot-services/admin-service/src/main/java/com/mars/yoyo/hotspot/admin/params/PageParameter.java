package com.mars.yoyo.hotspot.admin.params;

/**
 * 分页参数
 *
 * @author admin
 * @create 2018/9/1
 */
public class PageParameter extends SessionParameter {

    /**
     * 页数
     */
    private Integer pageNumber = 1;

    /**
     * 分页记录数
     */
    private Integer pageSize = Integer.valueOf(10);

    public PageParameter() {
    }

    public PageParameter(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        if (pageNumber.intValue() == 0) {
            pageNumber += 1;
        }
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if(pageNumber.intValue() <= 1) {
            pageNumber = Integer.valueOf(1);
        }
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize.intValue() < 1) {
            pageSize = Integer.valueOf(10);
        }
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return (this.pageNumber - 1) * this.pageSize;
    }

    public Integer getEnd() {
        return this.pageNumber * this.pageSize;
    }


}

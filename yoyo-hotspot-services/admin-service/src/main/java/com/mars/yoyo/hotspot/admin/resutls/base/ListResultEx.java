package com.mars.yoyo.hotspot.admin.resutls.base;

import java.util.List;

/**
 * 集合结果参数
 *
 * @author admin
 * @Date 2018/8/31 10:57
 */
public class ListResultEx<T> extends ResultEx {

    private static final long serialVersionUID = 1L;

    private Integer totalCount = 0;

    private Integer pageNumber;

    private Integer pageSize;

    public ListResultEx() {
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumbero(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getDataList() {
        Object data = this.getData();
        return data != null && data instanceof List?(List)data:null;
    }

    public void setDataList(List<T> list) {
        super.setData(list);
    }

    public ListResultEx<T> makeResult(Integer error, String message, List<T> list) {
        super.makeResult(error, message, list);
        return this;
    }

    @Override
    public ListResultEx<T> makeResult(Integer error) {
        return this.makeResult(error, (String)null, (List)null);
    }

    public ListResultEx<T> makeResult(Result result) {
        return this.makeResult(result.getRetCode(), result.getRetMsg(), (List)result.getData());
    }

    @Override
    public ListResultEx<T> makeSuccessResult() {
        return this.makeResult(1);
    }

    @Override
    public ListResultEx<T> makeFailedResult() {
        return this.makeResult(2);
    }

    @Override
    public ListResultEx<T> makeFailedResult(String message) {
        return this.makeResult(Integer.valueOf(2), message, (List)null);
    }

}

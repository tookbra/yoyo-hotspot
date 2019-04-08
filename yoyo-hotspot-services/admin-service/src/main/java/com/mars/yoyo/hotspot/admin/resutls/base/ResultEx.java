package com.mars.yoyo.hotspot.admin.resutls.base;

/**
 * 结果对象
 *
 * @author admin
 * @Date 2018/8/31 10:55
 */
public class ResultEx extends Result {

    private static final long serialVersionUID = 1L;

    public ResultEx() {
    }

    public boolean isSuccess() {
        Integer retCode = this.getRetCode();
        return retCode != null && retCode.intValue() == 1;
    }

    public boolean isFailed() {
        return !this.isSuccess();
    }

    public ResultEx makeResult(Integer retCode, String retMsg, Object data) {
        if(retCode != null) {
            this.setRetCode(retCode);
        }

        if(retMsg != null) {
            this.setRetMsg(retMsg);
        }

        if(data != null) {
            this.setData(data);
        }

        return this;
    }

    public ResultEx makeResult(Integer retCode, String retMsg) {
        return this.makeResult(retCode, retMsg, null);
    }

    public ResultEx makeResult(Integer retCode) {
        return this.makeResult(retCode, null, null);
    }

    public ResultEx makeSuccessResult() {
        return this.makeResult(1);
    }

    public ResultEx makeFailedResult() {
        return this.makeResult(2);
    }

    public ResultEx makeFailedResult(String retMsg) {
        return this.makeResult(Integer.valueOf(2), retMsg, null);
    }

}

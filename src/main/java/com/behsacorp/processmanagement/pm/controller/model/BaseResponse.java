package com.behsacorp.processmanagement.pm.controller.model;

public class BaseResponse {

    protected String resultDescription;
    protected int resultCode;

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}

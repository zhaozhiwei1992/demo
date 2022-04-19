package com.example.springbootactivity.activiti5.dto;

public class ProcessResult<K> {
    private String code;
    private String message;
    private boolean success;
    private K result;

    public ProcessResult() {
    }

    public ProcessResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public K getResult() {
        return this.result;
    }

    public void setResult(K result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ProcessResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", result=" + result +
                '}';
    }
}

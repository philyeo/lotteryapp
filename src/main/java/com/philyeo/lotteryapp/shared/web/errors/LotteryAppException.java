package com.philyeo.lotteryapp.shared.web.errors;

public class LotteryAppException extends RuntimeException {
    private final String detail;
    private final String errorCode;

    public LotteryAppException(String message, String errorCode, String detail) {
        super(message);
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

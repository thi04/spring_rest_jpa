package com.example.spring_jdbc.exception;



public enum ErrorCode {
    USER_EXISTED(1001,"..đã tồn tại..."),
    UNCATEGORIZED_EXCEPTION(9999,"not fond"),
    USERNAME_INVALID(1003,"TỐI THIÊU 3 KI TU"),
    INVALID_PASSWORD(1004,"TÔI THIỂU 8 KÍ TỰ"),
    INVALID_KEY(1005,"KHOÁ ko hop lệ")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
private String message;


    public int getCode() {
        return code;

    }

    public String getMessage() {
        return message;
    }
}

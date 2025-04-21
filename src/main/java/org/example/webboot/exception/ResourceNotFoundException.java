package org.example.webboot.exception;

public class ResourceNotFoundException extends RuntimeException {

    // 构造函数
    public ResourceNotFoundException(String message) {
        super(message); // 调用父类构造函数
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause); // 调用父类构造函数，传入错误信息和异常
    }
}

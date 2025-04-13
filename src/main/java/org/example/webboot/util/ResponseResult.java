package org.example.webboot.util;
//src/main/java/org/example/webboot/util/ResponseResult.java
public class ResponseResult {

    private String message;
    private Object data;

    public ResponseResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}

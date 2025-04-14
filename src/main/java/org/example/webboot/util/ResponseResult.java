package org.example.webboot.util;

public class ResponseResult {

    private String message;
    private Object data;
    private boolean success;

    public ResponseResult() {
    }

    public ResponseResult(String s) {
    }

    public static ResponseResult success(String message, Object data) {
        ResponseResult result = new ResponseResult();
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static ResponseResult error(String message) {
        ResponseResult result = new ResponseResult();
        result.setMessage(message);
        result.setData(null);  // 错误时可以设置为 null
        return result;
    }

    public ResponseResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    // 添加 setMessage 和 setData 方法
    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}

package org.example.webboot.exception;

import org.example.webboot.util.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("内部服务器错误: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseResult> handleIllegalArgumentException(IllegalArgumentException ex) {
        // 记录非法请求的异常
        logger.warn("Invalid request: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseResult("Invalid request: " + ex.getMessage()));
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseResult> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        // 记录分页错误的异常
        logger.warn("Pagination error: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseResult("Pagination error: " + ex.getMessage()));
    }

    // 其他常见异常的处理可以继续扩展
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseResult> handleNullPointerException(NullPointerException ex) {
        logger.error("Null pointer exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseResult("Null pointer exception: " + ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseResult> handleRuntimeException(RuntimeException ex) {
        logger.error("Runtime exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseResult("Runtime exception: " + ex.getMessage()));
    }
}

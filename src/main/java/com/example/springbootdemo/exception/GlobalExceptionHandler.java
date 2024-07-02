package com.example.springbootdemo.exception;

import com.example.springbootdemo.domain.R;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TestException.class)
    public R<?> handleException(HttpServletResponse response, Exception e) {
        log.error("测试自定义异常", e);
        return R.fail("测试自定义异常111111");
    }
}

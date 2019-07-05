package com.mallfe.common.advice;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResult> handleException(MallfeException e){
        ExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.status(em.getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}

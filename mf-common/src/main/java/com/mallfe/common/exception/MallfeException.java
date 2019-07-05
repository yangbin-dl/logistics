package com.mallfe.common.exception;

import com.mallfe.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MallfeException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}

package com.mallfe.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_DOUND(404,"商品分类未查到"),
    USER_OR_PASSWORD_NOT_CORRECT(404,"用户名或密码错误"),
    USERNAME_DUPLICATE(404,"用户名已存在")
    ;
    private int code;
    private String msg;
}

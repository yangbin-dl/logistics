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
    USERNAME_DUPLICATE(404,"用户名已存在"),
    SP_NOT_EXISTS(404,"商品不可用！"),
    HH_CANNOT_BE_NULL(400,"货号不能为空"),
    PINM_CANNOT_BE_NULL(400,"品名不能为空"),
    XINGH_CANNOT_BE_NULL(400,"型号不能为空"),
    CODE_CANNOT_BE_NULL(400,"编码不能为空"),
    NAME_CANNOT_BE_NULL(400,"名称不能为空"),
    USER_NOT_EXISTS(404,"用户不存在!"),
    USER_UPDATE_FAILURE(404,"用户名重复!"),
    DRIVER_NOT_EXISTS(404,"司机不存在!"),
    PATH_NOT_EXISTS(404,"路线不存在!"),
    BILL_SAVE_FALURE(404,"单据保存失败!"),
    GET_BILLBUMBER_FALURE(404,"获取单据号失败!"),
    BILL_NOT_EXISTS(404,"单据不存在!"),
    BILL_DETAIL_NOT_EXISTS(404,"单据明细不存在!"),
    OPERATION_FALURE(404,"操作失败!"),
    BILL_STATUS_ERROR(404,"单据状态异常!"),
    PL_NOT_EXISTS(404,"品类不存在！"),
    UNDER_STOCK(404,"库存不足！"),
    INVALID_FILE_TYPE(400,"无效的文件类型！"),
    ;
    private int code;
    private String msg;
}

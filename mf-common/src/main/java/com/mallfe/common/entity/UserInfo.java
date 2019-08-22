package com.mallfe.common.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Long id;
    private String username;
    private String token;

  /**
     * 店铺编码
     */
    private String storeCode;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 部门名称
     */
    private String deptName;
    private String truename;

    /**
     * 用户类型：0-管理员，1-库管员，2-开单员，3-审核员
     */
    private Integer lx;

    private String lxInfo;


    public UserInfo(Long id, String username,String truename) {
        this.id = id;
        this.username = username;
        this.truename = truename;
    }
    public UserInfo() {

    }
}

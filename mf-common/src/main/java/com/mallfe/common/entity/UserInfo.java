package com.mallfe.common.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Long id;
    private String username;
    private String token;


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

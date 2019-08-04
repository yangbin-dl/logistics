package com.mallfe.common.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Long id;
    private String username;

    public UserInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }
    public UserInfo() {

    }
}

package com.heowc.user.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRequest {

    private String id;
    private String name;
    private String address;
    private String zipCode;

    public User toUser() {
        return new User(id, name, address, zipCode);
    }
}

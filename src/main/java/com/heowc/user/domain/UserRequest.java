package com.heowc.user.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String address1;
    private String address2;

    public User toUser() {
        return new User(id, new Name(firstName, lastName), new Address(zipCode, address1, address2));
    }
}

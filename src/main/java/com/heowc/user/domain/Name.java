package com.heowc.user.domain;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Name {

    private String firstName;
    private String lastName;

    public String getFullName() {
        return this.firstName + this.lastName;
    }
}
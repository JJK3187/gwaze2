package com.gwaze2.user.dto;

import lombok.Getter;

@Getter
public class SignupUserResponse {

    private final Long id;
    private final String email;

    public SignupUserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}

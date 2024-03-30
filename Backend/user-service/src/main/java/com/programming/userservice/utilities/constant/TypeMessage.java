package com.programming.userservice.utilities.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeMessage {

    REGISTER("Register an account"),

    FORGET_PASSWORD("Forget password");

    private final String value;
}

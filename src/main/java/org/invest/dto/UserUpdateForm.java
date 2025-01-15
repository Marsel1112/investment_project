package org.invest.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserUpdateForm {
    private String email;
    private String name;
    private String lastName;
    private String surName;
    private String roleName;
}

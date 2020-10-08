package com.progmasters.reactblog.domain.dto;

import java.util.List;

public class RegistrationFormInitData {
    private List<RoleDto> roles;

    public RegistrationFormInitData(List<RoleDto> roles) {
        this.roles = roles;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}

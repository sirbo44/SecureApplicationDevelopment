package com.nyc.hosp.model;

import jakarta.validation.constraints.Size;


public class RoleDTO {

    private Integer roleId;

    @Size(max = 100)
    private String rolename;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(final String rolename) {
        this.rolename = rolename;
    }

}

package com.eipulse.teamproject.dto;


import lombok.Getter;

@Getter
public class LoginDTO {
    private Integer empId;
    private String password;

    public LoginDTO() {
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "empId=" + empId +
                ", password='" + password + '\'' +
                '}';
    }
}

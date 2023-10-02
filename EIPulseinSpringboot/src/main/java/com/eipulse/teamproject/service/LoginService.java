package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Login;
import jakarta.servlet.http.HttpSession;

public interface LoginService {

    Login checkLogin(Integer empId,String password);

    boolean forgetPassword(String email,Integer otpCheck,String newPassword);


    String updatePassword(Integer empId,String oldPassword,String newPassword);

    void logout(HttpSession httpSession);

}

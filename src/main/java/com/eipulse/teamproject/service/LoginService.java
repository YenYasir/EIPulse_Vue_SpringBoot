package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Login;
import jakarta.servlet.http.HttpSession;

public interface LoginService {

    Login checkLogin(Integer empId,String password);

    Login forgetPassword(String email,Integer otpVal);

    boolean newPassword(Login login,String newPassword);


    String updatePassword(Integer empId,String oldPassword,String newPassword);

    void logout(HttpSession httpSession);

}

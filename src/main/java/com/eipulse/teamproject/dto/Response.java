package com.eipulse.teamproject.dto;

import org.apache.poi.ss.formula.functions.T;

import com.eipulse.teamproject.enums.BaseEnum;
import com.eipulse.teamproject.enums.BusinessStatusEnum;

public class Response {

    public static ResponseDTO<T> success(){
        return new ResponseDTO<T>(BusinessStatusEnum.SUCCESS);
    }

    public static ResponseDTO<T> success(String message){
        return new ResponseDTO<T>(BusinessStatusEnum.SUCCESS.getCode(), message);
    }

    public static ResponseDTO<T> success(Object data){
        return new ResponseDTO<T>(BusinessStatusEnum.SUCCESS,data);
    }

    public static ResponseDTO<T> success(Object data,String token){
        return new ResponseDTO<T>(BusinessStatusEnum.SUCCESS,data,token);
    }

    public static ResponseDTO<T> success(String message, Object data){
        return new ResponseDTO<T>(BusinessStatusEnum.SUCCESS.getCode(),message,data);
    }

    public static ResponseDTO<T> error(String message){
        return new ResponseDTO<T>(BusinessStatusEnum.ERROR.getCode(), message);
    }

    public static ResponseDTO<T> error(){
        return new ResponseDTO<T>(BusinessStatusEnum.ERROR);
    }

    public static ResponseDTO<T> error(Integer code, String message){
        return new ResponseDTO<T>(code,message);
    }

    public static ResponseDTO<T> error(BaseEnum<T> e) {
        return new ResponseDTO<T>(e);
    }
}

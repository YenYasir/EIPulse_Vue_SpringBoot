package com.eipulse.teamproject.dto;

import com.eipulse.teamproject.enums.BaseEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@ApiModel(value = "數據傳輸對象", description = "")
	public class ResponseDTO<T> {
	    @ApiModelProperty("狀態碼")
	    private int code;

	    @ApiModelProperty("回應")
	    private String message;

	    @ApiModelProperty("數據")
	    private Object data;

	    @ApiModelProperty("token")
	    private String token;

	    public ResponseDTO(int code, String message){
	        this.code = code;
	        this.message = message;
	    }

	    public ResponseDTO(int code,String message,Object data){
	        this.code = code;
	        this.message = message;
	        this.data = data;
	    }

	    public ResponseDTO(BaseEnum<T> e){
	        this.code = e.getCode();
	        this.message = e.getMessage();
	    }

	    public ResponseDTO(BaseEnum<T> e, Object data){
	        this.code = e.getCode();
	        this.message = e.getMessage();
	        this.data = data;
	    }

	    public ResponseDTO(BaseEnum<T> e, Object data, String token){
	        this.code = e.getCode();
	        this.message = e.getMessage();
	        this.data = data;
	        this.token = token;
	    }

	}

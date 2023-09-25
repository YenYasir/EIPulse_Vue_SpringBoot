package com.eipulse.teamproject.model;

public class RespBean {
    private Integer status;
    private String message;
    private Object object;

    public static RespBean ok(String message){
        return new RespBean(200, message, null);
    }
    public static RespBean ok(String message, Object object){
        return new RespBean(200, message, object);
    }
    public static RespBean error(String message){
        return new RespBean(500, message, null);
    }
    public static RespBean error(String message, Object object){
        return new RespBean(200, message, object);
    }

    private RespBean() {
    }

    private RespBean(Integer status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public static RespBean build() {
        return new RespBean();
    }

    public Integer getStatus() {
        return status;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
    
    

}
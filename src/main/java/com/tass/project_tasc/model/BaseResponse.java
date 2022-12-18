package com.tass.project_tasc.model;

import com.tass.project_tasc.model.ERROR;
import lombok.Data;

@Data
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public BaseResponse(){
        this.code = 1;
        this.message = "SUCCESS";
    }

    public BaseResponse(ERROR error){
        this.code = error.code;
        this.message = error.message;
    }

    public BaseResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

package com.cloud.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterMessage implements Serializable{

    private static final long serialVersionUID = 1145681931807662807L;
    private String phone;
    private String data;

    public RegisterMessage() {
    }

    public RegisterMessage(String phone, String data) {
        this.phone = phone;
        this.data = data;
    }
}

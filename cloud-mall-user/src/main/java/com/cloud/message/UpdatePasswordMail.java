package com.cloud.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordMail implements Serializable{
    private static final long serialVersionUID = 7863314820852210510L;
    private String email;
    private String subject;
    private String content;
    private boolean html;
}

package com.cloud.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InvoiceTemplateDTO implements Serializable {

    private static final long serialVersionUID = -3297520339160003836L;

    private Long templateId;

    private Long userId;

    private Integer invoiceType;

    private String invoiceTitle;

    private String taxNo;

    private String companyName;

    private String registerAddress;

    private String registerMobile;

    private String depositBank;

    private String bankNo;

    private String receiverName;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverRegion;

    private String receiverAddress;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}

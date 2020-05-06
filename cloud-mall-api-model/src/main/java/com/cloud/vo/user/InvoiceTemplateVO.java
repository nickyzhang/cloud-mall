package com.cloud.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InvoiceTemplateVO implements Serializable {

    private static final long serialVersionUID = 8564799458416261559L;

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
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}

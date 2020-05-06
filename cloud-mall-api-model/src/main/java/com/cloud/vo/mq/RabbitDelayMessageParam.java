package com.cloud.vo.mq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "/catalogRabbitDelayMessageParam", description = "/catalog延迟队列消息参数")
public class RabbitDelayMessageParam implements Serializable {

    @ApiModelProperty(value = "/catalog交换机", required = true)
    private String exchange;

    @ApiModelProperty(value = "/catalog路由键", required = true)
    private String routingKey;

    @ApiModelProperty(value = "/catalog发送的消息", required = true)
    private Object message;

    @ApiModelProperty(value = "/catalog延迟时间", required = true)
    private Long delayTime;

    @ApiModelProperty(value = "/catalog发送源")
    private String source;

    @ApiModelProperty(value = "/catalog是否是调式模式")
    private boolean debug = false;
}

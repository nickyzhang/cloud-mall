package com.cloud.vo.mq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value = "/catalogRabbitMessageParam", description = "/catalog队列消息参数")
public class RabbitMessageParam implements Serializable {

    @ApiModelProperty(value = "/catalog交换机", required = true)
    private String exchange;

    @ApiModelProperty(value = "/catalog路由键", required = true)
    private String routingKey;

    @ApiModelProperty(value = "/catalog发送的消息", required = true)
    private Object message;

    @ApiModelProperty(value = "/catalog发送源", required = false)
    private String source;

    @ApiModelProperty(value = "/catalog是否是调式模式")
    private boolean debug = false;
}

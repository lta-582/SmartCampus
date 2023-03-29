package com.taian.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "Vote对象", description = "")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("投票目标ID")
    private Integer votableId;

    @ApiModelProperty("投票类型")
    private String type;


}

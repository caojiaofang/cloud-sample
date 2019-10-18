package com.cloud.info.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstancePOJO {

	private String serviceId;	// 服务ID
	private String host;	// 地址
	private String port;	// 端口
	private String state;	// 运行状态

}
package com.cloud.info.pojo;

import com.netflix.appinfo.InstanceInfo.InstanceStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InstanceDTO {

	private String port;
	private String serviceId;
	private String fileName;
	private String host;	// 地址
	private InstanceStatus status;

	public InstanceDTO() {
	}

	public InstanceDTO(String port, String serviceId, String host, InstanceStatus status) {
		this.port = port;
		this.serviceId = serviceId;
		this.host = host;
		this.status = status;
	}

	public InstanceDTO(String fileName) {
		this.fileName = fileName;
	}

}

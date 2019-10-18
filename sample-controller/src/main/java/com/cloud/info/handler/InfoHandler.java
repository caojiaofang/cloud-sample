/*
 * 文件名：InfoHandler.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： InfoHandler.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月18日
 * 修改内容：新增
 */
package com.cloud.info.handler;
/**
 * @Title:  InfoHandler.java
 * @Package: com.cloud.info.handller.InfoHandler.java
 * @Description: Eureka服务操作
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月18日 上午11:52:49
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.db.dao.ServerManagerDao;
import com.cloud.db.pojo.ServerManagerPOJO;
import com.cloud.info.pojo.InstanceDTO;
import com.cloud.info.pojo.InstancePOJO;
import com.cloud.info.pojo.RegistryPOJO;
import com.cloud.utils.StringUtils;
import com.cloud.utils.httprequest.HttpRequestUtils;
import com.cloud.utils.json.SamJsonUtil;
import com.cloud.utils.shell.GanymedUtils;
import com.cloud.utils.shell.ShellResult;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InfoHandler {

	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private ServerManagerDao serverManagerDao;
	
	
	/**
	 * 获取所有的服务列表
	 * @return
	 */
	public String getDeploys() {
		List<InstancePOJO> result = new ArrayList<InstancePOJO>();
		List<String> listServiceNames = this.discoveryClient.getServices();
		for (String serviceName : listServiceNames) {
			List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress(serviceName, false);
			for (InstanceInfo instance : instances) {
				InstancePOJO instanceDO = new InstancePOJO();
				instanceDO.setServiceId(instance.getAppName());
				instanceDO.setHost(instance.getIPAddr());
				instanceDO.setPort(String.valueOf(instance.getPort()));
				instanceDO.setState(instance.getStatus().name());
				result.add(instanceDO);
			}
		}
		return SamJsonUtil.toJson(result);
	}
	
	
	/**
	 * 根据服务名和端口号获取每个服务的详细信息
	 * @param serviceName
	 * @param port
	 * @return
	 */
	public String getAllByDeploy(String serviceName, String addr, String port) {
		List<InstanceDTO> result = new ArrayList<InstanceDTO>();
		List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress(serviceName, false);
		for (InstanceInfo instance : instances) {
			if (String.valueOf(instance.getPort()).equals(port) && instance.getIPAddr().equals(addr)) {
				InstanceDTO instanceDTO = new InstanceDTO();
				instanceDTO.setPort(String.valueOf(instance.getPort()));
				instanceDTO.setServiceId(instance.getAppName());
				instanceDTO.setFileName(instance.getIPAddr() + ":" + instance.getPort());
				instanceDTO.setHost(instance.getIPAddr());
				instanceDTO.setStatus(instance.getStatus());
				log.info("获取到的服务信息instanceDTO:{}",instanceDTO);
				result.add(instanceDTO);
			}
		}
		return SamJsonUtil.toJson(result);
	}
	
	/**
	 * 根据ip地址和端口号停止服务
	 * @param addr
	 * @param port
	 * @return
	 */
	public String handleStop(String addr, String port) {
		if (!StringUtils.isEmpty(addr) && !StringUtils.isEmpty(port)) {
			List<String> listServiceNames = this.discoveryClient.getServices();
			for (String serviceName : listServiceNames) {
				List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress(serviceName, false);
				for (InstanceInfo instance : instances) {
					if (addr.equals(instance.getIPAddr()) && port.equals(String.valueOf(instance.getPort()))) {
						log.info("停止的时候获取到的url地址:"+"http://"+addr+":"+port+"/actuator/service-registry");
						RegistryPOJO registryVO = new RegistryPOJO();
						registryVO.setStatus("DOWN");
						String httpPost = HttpRequestUtils.httpPost("http://"+addr+":"+port+"/actuator/service-registry", SamJsonUtil.toJson(registryVO), "application/json");
						String result = "error";
						ServerManagerPOJO serverManagerPOJO = serverManagerDao.selectById(addr, port);
						if (!StringUtils.isEmpty(serverManagerPOJO)) {
							try {
								//执行停止脚本
								ShellResult shellResult = GanymedUtils.exec(addr, serverManagerPOJO.getUsername(), serverManagerPOJO.getUserpwd(), serverManagerPOJO.getFilepath(), StandardCharsets.UTF_8);
								result = shellResult.getResult();
								log.info("获取到的输出结果shellResult:{}",shellResult);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return result;
						}
					} 
				}
			}
		}
		return "error";
	}
}

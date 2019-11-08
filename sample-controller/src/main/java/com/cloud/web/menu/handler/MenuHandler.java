/*
 * 文件名：MenuHandler.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MenuHandler.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月29日
 * 修改内容：新增
 */
package com.cloud.web.menu.handler;

import java.awt.peer.MenuPeer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloud.base.PagerView;
import com.cloud.db.dao.SampleUserDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title:  MenuHandler.java
 * @Package: com.cloud.web.menu.handler.MenuHandler.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月29日 上午11:43:01
 */
@Slf4j
@Service
public class MenuHandler {

	@Resource
	private SampleUserDao sampleUserDao;
	
//	public PagerView<MenuP> doMain(Map<String, Object> map) {
//
//        PageBounds pageBounds = getPageBounds(map);
//
//        List<UppMenuDO> listUppMenuDO = uppMenuDao.selectByMap(map, pageBounds);
//
//        return getPagerView(pageBounds.getCountRows(), listUppMenuDO, UppMenuVO.class);
//
//    }
}

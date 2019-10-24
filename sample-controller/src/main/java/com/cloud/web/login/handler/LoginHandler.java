/*
 * 文件名：LoginHandler.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LoginHandler.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.web.login.handler;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.common.enums.UserStateEnum;
import com.cloud.context.WebContextPOJO;
import com.cloud.context.WebContextUtil;
import com.cloud.db.dao.SampleUserDao;
import com.cloud.db.pojo.SampleUserPOJO;
import com.cloud.utils.StringUtils;
import com.cloud.utils.date.DateUtils;
import com.cloud.utils.tools.MD5Util;


/**
 * @Title:  LoginHandler.java
 * @Package: com.cloud.web.login.handler.LoginHandler.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:38:50
 */
@Service
public class LoginHandler {

	private static Logger logger = LoggerFactory.getLogger(LoginHandler.class); // 日志记录
	
	public static final String YMDHMS_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	@Resource
	private SampleUserDao sampleUserDao;
	
	
	public String doLogin(String account, String password, HttpSession session) {
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password) || session == null) {
			return "userAcctError";
		}
		SampleUserPOJO sampleUserPOJO = sampleUserDao.selectById(account); 
		if (sampleUserPOJO == null) {
			return "userAcctError";
		}
		if (!isCanLogin(sampleUserPOJO)) {
			return sampleUserPOJO.getUserState();
		}
		if (sampleUserPOJO.getUserPwd().equals(MD5Util.getMD5(password))) {
			WebContextPOJO webContextPOJO = new WebContextPOJO();
			webContextPOJO.setUserAcct(sampleUserPOJO.getUserAcct());
			webContextPOJO.setUserNm(sampleUserPOJO.getUserNm());
			webContextPOJO.setLoginTime(DateUtils.format(new Date(), YMDHMS_PATTERN));
			webContextPOJO.setSampleUserPOJO(sampleUserPOJO);
			WebContextUtil.key(webContextPOJO, session);
			WebContextUtil.setUserContext(webContextPOJO, session);
			sampleUserPOJO.setPwdErrCnt(0);
			sampleUserPOJO.setLastLoginTm(webContextPOJO.getLoginTime());
			sampleUserPOJO.setUpdTm(webContextPOJO.getLoginTime());
			sampleUserDao.updateByIdSelective(sampleUserPOJO);
			logger.info("用户{}登录成功,登录日期:{}！",account,webContextPOJO.getLoginTime());
			return "success";
		} else {
			int cnt = 0;
			if (sampleUserPOJO.getPwdErrCnt() != null) {
				cnt = sampleUserPOJO.getPwdErrCnt();
			}
			cnt++;
			sampleUserPOJO.setPwdErrCnt(cnt);
			if (cnt > 5) {
				sampleUserPOJO.setUserState(UserStateEnum.LOCK.getEnValue());
			}
			sampleUserPOJO.setUpdTm(DateUtils.format(new Date(), YMDHMS_PATTERN));
			sampleUserDao.updateByIdSelective(sampleUserPOJO);
			logger.info("用户{}第{}次密码输入错误",account,cnt);
			return "userPwdError";
		}
	}
	
	/**
	 * 方法描述：判断用户状态是否能正常登陆，如果能返回true，反之返回false
	 * @return Boolean
	 */
	private Boolean isCanLogin(SampleUserPOJO sampleUserPOJO){
		if(UserStateEnum.STOP.getEnValue().equals(sampleUserPOJO.getUserState())){
			return false;
		}else if(UserStateEnum.LOCK.getEnValue().equals(sampleUserPOJO.getUserState())){
			return false;
		}else if(UserStateEnum.DEL.getEnValue().equals(sampleUserPOJO.getUserState())){
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.err.println(MD5Util.getMD5("123456"));
	}
}

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloud.context.WebContextPOJO;
import com.cloud.db.dao.UserDao;
import com.cloud.db.pojo.UserPOJO;
import com.cloud.utils.StringUtils;
import com.cloud.utils.date.DateUtils;

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
	private UserDao userDao;
	
	
	public String doLogin(String account, String password) {
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
			return "userAcctError";
		}
		UserPOJO userPOJO = userDao.selectById(account);
		if (userPOJO == null) {
			return "userAcctError";
		}
		/*
		 * if (userPOJO.getUserPwd().equals(password)) { WebContextPOJO webContextPOJO =
		 * new WebContextPOJO(); webContextPOJO.setUserAcct(userPOJO.getUserAcct());
		 * webContextPOJO.setUserNm(userPOJO.getUserNm());
		 * webContextPOJO.setLoginTime(DateUtils.format(new Date(), YMDHMS_PATTERN));
		 * webContextPOJO.setUserPOJO(userPOJO); UppContextUtil.key(vo, session);
		 * UppContextUtil.setContext(vo, session); //设置sessionId String ip =
		 * (((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest()).getRemoteAddr();
		 * if(StringUtil.isEmptyOrNull(uppUserDO.getSessionId())) {
		 * uppUserDO.setSessionId(ip); }else if(!ip.equals(uppUserDO.getSessionId()))
		 * {//当前登录ip与上次登录ip 不一致 return "ipError"; } uppUserDO.setPwdErrCnt(0);
		 * uppUserDao.updateByIdSelective(uppUserDO); log.info("用户{}登录成功。",
		 * uppUserDO.getUserAcct());
		 * 
		 * return "success"; } else { int cnt = 0; if(uppUserDO.getPwdErrCnt() != null)
		 * { cnt = uppUserDO.getPwdErrCnt(); } cnt++; uppUserDO.setPwdErrCnt(cnt);
		 * if(cnt > 4) { uppUserDO.setState(UserStateEnum.LOCK.getEnValue()); }
		 * uppUserDao.updateByIdSelective(uppUserDO); return "userPwdError|" + cnt; }
		 */
		return password;
	}
	
	
}

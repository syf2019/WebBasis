package com.ims.shiro.Realm;


import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.ims.common.constant.IMSCons;
import com.ims.common.util.IMSUtil;
import com.ims.system.constant.SystemCons;
import com.ims.system.model.User;
import com.ims.system.util.CacheCxt;


public class UserRealm extends AuthorizingRealm{
	
	
	  /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
    	
        return token instanceof TokenRealm;
    }
    /**
     * 访问权限控制
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String token = principals.toString();
	    User user=CacheCxt.getUserToken(token);
		Set<String> perms =new HashSet<String>();
	    if(IMSUtil.isNotEmpty(user)){
	        String whetherSuper=IMSCons.WHETHER_NO;
	        if(SystemCons.SUPER_ADMIN.equals(user.getAccount())){
	        	whetherSuper=IMSCons.WHETHER_YES;
	        }
	    	perms=CacheCxt.getAuthPermissions(user.getUserId(),whetherSuper);
	    }
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		
		String token = (String) authToken.getCredentials();
		if(IMSUtil.isEmpty(token)){
			throw new AuthenticationException("token为空，系统拒绝访问");
		}
		if(CacheCxt.checkAndRefreshToken(token)){
			return new SimpleAuthenticationInfo(token, token, "userRealm");
		}
		throw new AuthenticationException("token过期");
	}

}

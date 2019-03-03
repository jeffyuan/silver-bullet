package com.silverbullet.auth.security.shiro.config;

import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthActionService;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.auth.sysconfig.SysDictionary;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.security.SecurityMethod;
import com.silverbullet.security.SilverbulletEncrypt;
import org.apache.shiro.authc.*;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by GESOFT on 2017/11/14.
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    final static String AUTHCACHENAME = "AUTHCACHENAME";

    @Autowired
    private ISysAuthUserService iSysAuthUserService;
    @Autowired
    private ISysAuthPostService iSysAuthPostService;
    @Autowired
    private ISysAuthActionService iSysAuthActionService;
    @Autowired
    private SilverbulletEncrypt silverbulletEncrypt;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo)principals.getPrimaryPrincipal();

        //SysAuthUser userInfo = iSysAuthUserService.getOneByUserName(username);

        // 设置角色权限   @RequiresRoles("ADMIN")
        authorizationInfo.addRole(SysDictionary.getRoleNo(userInfo.getUserType()));

        // 获取岗位
        List<SysAuthPost> userPosts = iSysAuthPostService.getPostList(ToolUtil.toString(userInfo.getId()));
        for(SysAuthPost role:userPosts){
            List<SysAuthAction> listActions = iSysAuthActionService.getPostActionList(role.getId());
            // 获取功能权限
            for(SysAuthAction p: listActions){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");

        //获取用户的输入的账号.
        UsernamePasswordToken userToken = (UsernamePasswordToken)authenticationToken;

        // 用RSA进行解密处理
        try {
            userToken.setUsername(silverbulletEncrypt.decrypt(SecurityMethod.RSA, userToken.getUsername()));
            userToken.setPassword(silverbulletEncrypt.decrypt(SecurityMethod.RSA, String.valueOf(userToken.getPassword())).toCharArray());
            userToken.setRememberMe(userToken.isRememberMe());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户信息解密失败。");
            return null;
        }

        authenticationToken = userToken;

        System.out.println(authenticationToken.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysAuthUser userInfo = iSysAuthUserService.getOneByUserName(userToken.getUsername());
        System.out.println("----->>userInfo="+userInfo);
        if(userInfo == null){
            return null;
        }

        UserInfo user = new UserInfo();
        user.setId(userInfo.getId());
        user.setName(userInfo.getName());
        user.setUsername(userInfo.getUsername());
        user.setUserType(userInfo.getUserType());
        user.setLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userInfo.getLoginTime()));

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getSalt()),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }


    /**
     * 明文进行密码进行加密
     * @param args
     */
    public static void main(String[] args) {
        int hashIterations = 10;//加密的次数
        Object salt = "11";//盐值
        Object credentials = "SYSADMIN";//密码
        String hashAlgorithmName = "md5";//加密方式
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
                salt, hashIterations);
        System.out.println("加密后的值----->" + simpleHash);
    }
}

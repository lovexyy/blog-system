package com.blog.common.shiro;

import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @program: blog-admin
 * @description: shiro 拓展 Realm
 * @author: Zx
 * @create: 2022-08-03 10:52
 **/
@Component
@Slf4j
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    /**
     * 告诉 AuthenticationToken 是自定义的 JwtToken
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    /**
     * 获取认证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 将shiro token 转换为 我自定义token类
        JwtToken jwtToken = (JwtToken) token;
        // 从token 信息中获取 用户id
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        // 获取用户信息
        User user = userService.getById(Long.valueOf(userId));
        Optional.ofNullable(user).orElseThrow(() -> new UnknownAccountException("账户不存在"));

        if (user.getStatus() == -1) {
            throw new LockedAccountException("账户已锁定");
        }
        log.info("---------------------------------");

        AccountProfile profile = new AccountProfile();
        BeanUtils.copyProperties(user, profile);

        // 通过 shiro 返回账户信息
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}

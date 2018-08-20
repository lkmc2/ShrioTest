package com.linchange;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lkmc2
 * @date 2018/8/2
 * @description 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {

    // 用来模拟数据库（用户密码表）
    private Map<String, String> userMap = new HashMap<String, String>(16);

    {
        super.setName("myRealm"); // 设置自定义Realm的名字
        userMap.put("Cindy", "120");
    }

    // 用于做授权（用户角色、权限等）
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.从主体传过来的认证信息中，获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();

        // 2.通过用户名从数据库中或者缓存中获取用户角色信息
        Set<String> roles = getRolesByUserName(userName);

        // 3.通过用户名从数据库中或者缓存中获取用户权限信息
        Set<String> permissions = getPermissionsByUserName(userName);

        // 4.创建简单授权信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles); // 设置角色信息
        authorizationInfo.setStringPermissions(permissions); // 设置授权信息

        return authorizationInfo;
    }

    /**
     * 模拟从数据库中获取权限信息
     * @param userName 用户名
     * @return 用户对应的权限列表
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> permissionsSet = new HashSet<>();
        permissionsSet.add("user:add");
        permissionsSet.add("user:delete");
        return permissionsSet;
    }

    /**
     * 模拟从数据库中获取角色信息
     * @param userName 用户名
     * @return 用户对应的角色名列表
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> rolesSet = new HashSet<>();
        rolesSet.add("admin");
        rolesSet.add("user");
        return rolesSet;
    }

    // 用于做认证（检查用户密码是否正确）
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        // 2.通过用户名到数据库中获取凭证
        String password = getPasswordByUsername(userName);

        if (password == null) {
            return null;
        }

        // 生成简单认证信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("Cindy", "120", "myRealm");

        return authenticationInfo;
    }

    /**
     * 模拟数据库查询凭证
     * @param userName 用户名
     * @return 对应的密码
     */
    private String getPasswordByUsername(String userName) {
        return userMap.get(userName);
    }

}

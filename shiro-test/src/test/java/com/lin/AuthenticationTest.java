package com.lin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lkmc2
 * @date 2018/8/2
 * @description 认证类测试
 */
public class AuthenticationTest {

    // 简单用户领域角色（需要在Realm中有注册，才可以通过login认证）
    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("Andy", "123"); // 在用户领域中添加账户和密码
    }

    @Test
    public void testAuthentication() {
        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm); // 设置用户领域角色

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Andy", "123"); // 生成token
        subject.login(token); // 使用token进行登陆（Andy在Realm中有注册，登陆成功）

        System.out.println("Andy是否认证：" + subject.isAuthenticated()); // true

        subject.logout(); // 退出登陆

        System.out.println("Andy是否认证：" + subject.isAuthenticated()); // false

        UsernamePasswordToken jackToken = new UsernamePasswordToken("jack", "123"); // 生成token
        subject.login(jackToken); // 使用token进行登陆（jack在Realm没有注册，直接报错）


    }

}

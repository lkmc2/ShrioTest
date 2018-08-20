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
 * @description 授权类测试
 */
public class AuthenticationTest2 {

    // 简单用户领域角色（需要在Realm中有注册，才可以通过login认证）
    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
//        // 在用户领域中添加账户和密码，并设置admin角色
//        simpleAccountRealm.addAccount("Andy", "123", "admin");

        // 在用户领域中添加账户和密码，并设置admin和user角色
        simpleAccountRealm.addAccount("Andy", "123", "admin", "user");
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

        subject.checkRole("admin"); // 检查用户是否具备admin角色

        subject.checkRoles("admin", "user"); // 检查用户是否具备admin和user角色

        subject.logout(); // 退出登陆

        System.out.println("Andy是否认证：" + subject.isAuthenticated()); // false

    }

}

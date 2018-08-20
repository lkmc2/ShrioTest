package com.lin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author lkmc2
 * @date 2018/8/2
 * @description IniRealm测试类
 */
public class IniRealmTest {


    @Test
    public void testAuthentication() {

        // 初始化IniRealm（将读取配置文件中的用户信息）
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Andy", "123"); // 生成token
        subject.login(token); // 使用token进行登陆（Andy在Realm中有注册，登陆成功）

        System.out.println("Andy是否认证：" + subject.isAuthenticated()); // true

        subject.checkRole("admin"); // 检查用户是否具有admin授权

        subject.checkPermission("user:delete"); // 检查用户权限

    }

}

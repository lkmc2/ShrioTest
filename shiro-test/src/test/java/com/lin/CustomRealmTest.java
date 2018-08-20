package com.lin;

import com.linchange.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author lkmc2
 * @date 2018/8/2
 * @description 自定义Realm测试
 */
public class CustomRealmTest {

    @Test
    public void testAuthentication() {

        CustomRealm customRealm = new CustomRealm(); // 自定义Realm

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm); // 设置自定义Realm

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Cindy", "120"); // 生成token
        subject.login(token); // 使用token进行登陆（Andy在Realm中有注册，登陆成功）

        System.out.println("Andy是否认证：" + subject.isAuthenticated()); // true

        subject.checkRole("admin"); // 检查用户是否具有admin授权

        subject.checkPermissions("user:add", "user:delete"); // 检查用户权限

    }

}

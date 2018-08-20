package com.lin;

import com.linchange.UsePassCustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author lkmc2
 * @date 2018/8/2
 * @description 自定义Realm测试（使用加密）
 */
public class UsePassCustomRealmTest {

    @Test
    public void testAuthentication() {

        UsePassCustomRealm usePassCustomRealm = new UsePassCustomRealm(); // 自定义Realm

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(usePassCustomRealm); // 设置自定义Realm

        // 创建加密器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5"); // 使用md5加密
        matcher.setHashIterations(1); // 设置加密次数

        // 给自定义Realm设置加密器
        usePassCustomRealm.setCredentialsMatcher(matcher);

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Cindy", "1234567"); // 生成token
        subject.login(token); // 使用token进行登陆（Andy在Realm中有注册，登陆成功）

        System.out.println("Andy是否认证：" + subject.isAuthenticated()); // true

//        subject.checkRole("admin"); // 检查用户是否具有admin授权
//
//        subject.checkPermissions("user:add", "user:delete"); // 检查用户权限

    }

//    public static void main(String[] args) {
////        // 获取密码的md5值
////        Md5Hash md5Hash = new Md5Hash("1234567");
////        System.out.println(md5Hash.toString());
//
//        // 获取密码的md5值（加盐，盐值：Cindy）
//        Md5Hash md5Hash = new Md5Hash("1234567", "Cindy");
//        System.out.println(md5Hash.toString());
//    }

}

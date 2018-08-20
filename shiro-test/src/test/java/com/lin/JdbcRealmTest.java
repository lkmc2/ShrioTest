package com.lin;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author lkmc2
 * @date 2018/8/2
 * @description IniRealm测试类
 */
public class JdbcRealmTest {

    // 数据库数据源
    private DruidDataSource dataSource = new DruidDataSource();

    // 配置数据库信息
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro_test?serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void testAuthentication() {

        JdbcRealm jdbcRealm = new JdbcRealm(); // 创建jdbcRealm

        jdbcRealm.setDataSource(dataSource); // 设置数据源
        jdbcRealm.setPermissionsLookupEnabled(true); // 打开权限开关（默认为false）

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm); // 设置jdbcRealm

        // 2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Andy", "123"); // 生成token
        subject.login(token); // 使用token进行登陆（Andy在Realm中有注册，登陆成功）

        System.out.println("Andy是否认证：" + subject.isAuthenticated()); // true

        subject.checkRole("admin"); // 检查用户角色

        subject.checkRoles("admin", "user"); // 检查多个用户角色

        subject.checkPermission("user:select"); // 检查用户权限

    }

}

package com.example;


import com.example.entity.AdminExample;
import com.example.entity.Role;
import com.example.mapper.AdminMapper;
import com.example.mapper.RoleMapper;
import com.example.service.AdminService;
import com.example.entity.Admin;
import com.example.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml",
})
public class MybatisTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void testContext () throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testAdminService()
    {
        Admin admin =new Admin(null,"jerry","123456","杰瑞","jerry@qq.com",null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testMd5()
    {
        System.out.println("--------------------------------------");
        System.out.println(CrowdUtil.md5("123456"));
    }

    @Test
    public void addFakeUser()
    {
        String pas = "123456";
        String s = CrowdUtil.md5(pas);
        adminService.saveAdmin(new Admin(1,"jerry",s,"杰瑞","123456@qq.com",null));
        for (int i = 0; i < 218; i++) {

           adminService.saveAdmin(new Admin(i+2,"Tom"+i+2,s,"汤姆","123456@qq.com",null));
        }
    }
    @Test
    public void deleteFakeUser()
    {
        for (int i = 0; i <219 ; i++) {

            adminMapper.deleteByPrimaryKey(i);
        }
    }

    @Test
    public void addFakeRole()
    {
        for (int i = 0; i <50 ; i++) {
            roleMapper.insert(new Role(i+1,"Jack"+i));
        }
    }
}

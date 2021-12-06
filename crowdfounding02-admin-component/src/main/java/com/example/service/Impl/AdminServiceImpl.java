package com.example.service.Impl;

import com.example.Constant.CrowdConstant;
import com.example.Exception.LoginAccountAlreadyInUseException;
import com.example.Exception.LoginAccountAlreadyInUseForUpdateException;
import com.example.Exception.LoginFailedException;
import com.example.entity.Admin;
import com.example.entity.AdminExample;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import com.example.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void saveAdmin(Admin admin) {
        //对用户的密码进行加密然后重新加入到 用户数据中
        String s = CrowdUtil.md5(admin.getUserPswd());
        admin.setUserPswd(s);
        //获取当前的日期 然后加入到用户的创建时间中去
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        admin.setCreateTime(format);
        try{  adminMapper.insert(admin);}
        catch (Exception e)
        {
            //如果违反唯一键重复的异常 就抛出自定义的异常LoginAccountAlreadyInUse
            if(e instanceof DuplicateKeyException)
            {
                throw new LoginAccountAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }

    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        AdminExample adminExample = new AdminExample();

        AdminExample.Criteria criteria = adminExample.createCriteria();

        criteria.andLoginAcctEqualTo(loginAcct);

        List<Admin> list = adminMapper.selectByExample(adminExample);

        if(list==null||list.size()==0)
        {
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if(list.size()>1)
        {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = list.get(0);

        if(admin==null)
        {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        String userPswdDB = admin.getUserPswd();

        String userPswdFrom = CrowdUtil.md5(userPswd);

        if(!Objects.equals(userPswdDB,userPswdFrom))
        {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }


    @Override
    public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize) {
// 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);
// 2.查询 Admin 数据
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);
// ※辅助代码：打印 adminList 的全类名
        Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
        logger.debug("adminList 的全类名是："+adminList.getClass().getName());
// 3.为了方便页面使用将 adminList 封装为 PageInfo
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return pageInfo;
    }

    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
       return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginAccountAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {

        adminMapper.deleteOldRelationship(adminId);
        if(roleIdList!=null &&roleIdList.size()>0)
        {
            adminMapper.insertNewRelationship(adminId,roleIdList);
        }
    }
}

package com.example.service.Impl;


import com.example.entity.Role;
import com.example.entity.RoleExample;
import com.example.mapper.RoleMapper;
import com.example.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);
        return new PageInfo<>(roles);
    }
    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }
    // service 代码
    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria= example.createCriteria();
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminid) {
        return roleMapper.selectAssignedRole(adminid);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminid) {
        return roleMapper.selectUnAssignedRole(adminid);
    }
}

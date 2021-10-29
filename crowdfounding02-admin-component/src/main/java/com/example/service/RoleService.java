package com.example.service;

import com.example.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void saveRole(Role role);

    // service 代码
    void updateRole(Role role);

    void removeRole(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer adminid);

    List<Role> getUnAssignedRole(Integer adminid);
}

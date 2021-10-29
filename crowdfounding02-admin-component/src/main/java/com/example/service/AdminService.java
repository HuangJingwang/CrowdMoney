package com.example.service;

import com.example.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct,String userPswd);
    public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize);

    void deleteAdminById(Integer id);

    Admin getAdminById(Integer adminId);

    void updateAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}

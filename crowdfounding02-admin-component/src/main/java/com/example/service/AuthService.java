package com.example.service;

import com.example.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    public List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);
}

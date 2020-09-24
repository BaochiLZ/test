package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;

import java.util.List;

public interface IRoleService {
    public List<Role> findAll() throws Exception;

    void save(Role role);

    Role findById(String id);

    List<Permission> findOtherPermission(String id);

    void addPermissionToRole(String roleId, String[] permissionIds);
}

package dmuravsky.service;

import dmuravsky.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);
    void editRole(Role role);
    void deleteRole(Role role);
    Role getRoleByName(String name);
    Role getRoleById(int id);
    List<Role> getAllRoles();
}

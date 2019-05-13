package dmuravsky.service.impl;

import dmuravsky.dao.RoleDAO;
import dmuravsky.model.Role;
import dmuravsky.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;

    public void addRole(Role role) {
        roleDAO.addRole(role);
    }

    public void editRole(Role role) {
        roleDAO.editRole(role);
    }

    public void deleteRole(Role role) {
        roleDAO.deleteRole(role);
    }

    public Role getRoleByName(String name) {
        return roleDAO.getRoleByName(name);
    }

    public Role getRoleById(int id) {
        return roleDAO.getRoleById(id);
    }

    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }
}

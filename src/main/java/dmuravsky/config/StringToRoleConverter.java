package dmuravsky.config;

import dmuravsky.model.Role;
import dmuravsky.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    @Autowired
    RoleService roleService;

    @Override
    public Role convert(String id) {
        return roleService.getRoleById(Integer.parseInt(id));
    }
}

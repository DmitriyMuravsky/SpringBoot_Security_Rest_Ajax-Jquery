package dmuravsky.controller;

import dmuravsky.model.Role;
import dmuravsky.model.User;

import dmuravsky.service.RoleService;
import dmuravsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public List<User> users() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/admin/roles", method = RequestMethod.GET)
    public List<Role> roles() {
        return roleService.getAllRoles();
    }

    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") int id) {
        return userService.getOneUserById(id);
    }

    @PostMapping(value = "/admin/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> add(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/admin/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> edit(@PathVariable("id") int id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/admin/user/{id}")
    @ResponseBody
    public ResponseEntity<User> delete(@PathVariable("id") int id) {
        userService.deleteUser(userService.getOneUserById(id));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/admin/account", method = RequestMethod.GET)
    public User getInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}

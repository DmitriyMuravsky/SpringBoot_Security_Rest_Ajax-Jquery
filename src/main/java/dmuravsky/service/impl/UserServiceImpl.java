package dmuravsky.service.impl;

import dmuravsky.dao.UserDAO;
import dmuravsky.model.User;
import dmuravsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    public User getOneUserById(int id) {
        return userDAO.getOneUserById(id);
    }

    public User getOneUserByLogin(String login) {
        return userDAO.getOneUserByLogin(login);
    }
}

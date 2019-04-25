package dmuravsky.service;

import dmuravsky.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser (User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getOneUserById(int id);
    User getOneUserByLogin(String login);
}

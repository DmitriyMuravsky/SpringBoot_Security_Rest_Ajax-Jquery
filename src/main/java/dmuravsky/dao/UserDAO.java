package dmuravsky.dao;

import dmuravsky.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    void addUser (User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getOneUserById(int id);
    User getOneUserByLogin(String login);
}

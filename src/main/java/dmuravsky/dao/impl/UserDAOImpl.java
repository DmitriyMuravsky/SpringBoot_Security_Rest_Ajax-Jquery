package dmuravsky.dao.impl;

import dmuravsky.dao.UserDAO;
import dmuravsky.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void deleteUser(User user) {
        Query query = entityManager.createNativeQuery("DELETE FROM user where id=?");
        query.setParameter(1, user.getId());
        query.executeUpdate();
    }

    public User getOneUserById(int id) {
        return entityManager.find(User.class, id);
    }

    public User getOneUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }
}

package dmuravsky.dao.impl;

import dmuravsky.dao.RoleDAO;
import dmuravsky.model.Role;
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
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Transactional
    public void editRole(Role role) {
        entityManager.merge(role);
    }

    @Transactional
    public void deleteRole(Role role) {
        Query query = entityManager.createQuery("DELETE FROM roles WHERE id_role=?");
        query.setParameter(1, role.getId());
        query.executeUpdate();
    }

    public Role getRoleByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    public Role getRoleById(int id) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.id = :id", Role.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}

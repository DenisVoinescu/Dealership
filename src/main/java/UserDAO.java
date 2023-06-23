import entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;


public class UserDAO {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    public UserDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public void create(UserEntity user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateUser(UserEntity user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteUser(UserEntity user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        UserEntity managedUser = entityManager.find(UserEntity.class, user.getUserid());
        if (managedUser != null) {
            entityManager.remove(managedUser);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public UserEntity readUserByID(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        UserEntity user = entityManager.find(UserEntity.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }
    public UserEntity readUserByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username");
        query.setParameter("username", username);

        UserEntity user = (UserEntity) query.getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();

        return user;
    }

    public List<UserEntity> readAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM UserEntity u");
        List<UserEntity> userList = query.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return userList;
    }

    public void deleteAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM UserEntity ");
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public long exists(String username, String password) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username AND u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (Long) query.getSingleResult();
    }
}


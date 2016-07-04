package com.github.wiro34.hairspray.dummy_models;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.Getter;

@Named
@Dependent
public class UserRepository implements RepositoryOperation<User> {

    @Inject
    @Getter
    private EntityManager entityManager;
    
    @Override
    public Optional<User> find(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
    
    @Override
    public List<User> all() {
        return entityManager.createQuery("select u from User u order by u.id").getResultList();
    }
    
    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }
}

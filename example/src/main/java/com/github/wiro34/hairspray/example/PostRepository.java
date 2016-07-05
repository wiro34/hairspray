package com.github.wiro34.hairspray.example;

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
public class PostRepository implements RepositoryOperation<Post> {

    @Inject
    @Getter
    private EntityManager entityManager;

    @Override
    public Optional<Post> find(Long id) {
        return Optional.ofNullable(entityManager.find(Post.class, id));
    }

    public List<Post> findBy(User user) {
        return entityManager
                .createQuery("select p from Post p where p.auther.id = :userId order by p.id")
                .setParameter("userId", user.getId())
                .getResultList();
    }

    @Override
    public List<Post> all() {
        return entityManager
                .createQuery("select p from Post p order by p.id")
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Post post) {
        if (entityManager.contains(post) || post.getId() > 0) {
            entityManager.merge(post);
        } else {
            entityManager.persist(post);
        }
    }
}

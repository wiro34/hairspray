package com.github.wiro34.hairspray.example;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public interface RepositoryOperation<T> {

    public EntityManager getEntityManager();

    public Optional<T> find(Long id);

    public List<T> all();

    public void save(T t);

    public default Optional<T> first() {
        return all().stream().findFirst();
    }
    
    public default Optional<T> last() {
        return all().stream().reduce((first, second) -> second);
    }
}

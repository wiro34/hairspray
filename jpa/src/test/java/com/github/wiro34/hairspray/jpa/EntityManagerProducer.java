package com.github.wiro34.hairspray.jpa;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "ExampleDS")
    private EntityManager entityManager;
}

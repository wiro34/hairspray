package com.github.wiro34.hairspray;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.jglue.cdiunit.NgCdiRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class AbstractCdiTest extends NgCdiRunner {

    @Inject
    private EntityManager entityManager;

    private static EntityManagerFactory emf;

    @Produces
    @ApplicationScoped
    public EntityManager createEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("test");
        }
        return emf.createEntityManager();
    }

    @BeforeMethod(alwaysRun = true)
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @AfterMethod(alwaysRun = true)
    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
}

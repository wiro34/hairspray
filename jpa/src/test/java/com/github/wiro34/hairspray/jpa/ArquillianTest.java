package com.github.wiro34.hairspray.jpa;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.io.File;
import java.util.Arrays;

public class ArquillianTest extends Arquillian {
    @ArquillianResource
    private InitialContext initialContext;

    @Resource
    private UserTransaction userTransaction;

    protected boolean isInContainer() {
        return (null != initialContext);
    }

    @BeforeMethod
    public void beginTransaction() throws SystemException, NotSupportedException {
        if (isInContainer()) {
            userTransaction.begin();
        }
    }

    @AfterMethod
    public void endTransaction() throws SystemException {
        if (isInContainer()) {
            userTransaction.rollback();
        }
    }

    @Deployment(name = "default")
    public static WebArchive deploy() {
        final PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.github.wiro34.hairspray.jpa")
                .addAsLibraries(
                        pom.importRuntimeDependencies()
                                .resolve("org.testng:testng")
                                .withTransitivity()
                                .asFile()
                );
        Arrays.stream(new File("src/main/resources").listFiles()).forEach(war::addAsResource);
        Arrays.stream(new File("src/test/resources").listFiles()).forEach(war::addAsResource);
        System.out.println(war.toString(true));
        return war;
    }
}

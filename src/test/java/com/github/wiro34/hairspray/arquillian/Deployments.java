package com.github.wiro34.hairspray.arquillian;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

import java.io.File;
import java.util.Arrays;

@ArquillianSuiteDeployment
public class Deployments {
    @Deployment(name = "default")
    public static WebArchive deploy() {
        final PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.github.wiro34.hairspray")
                .addAsLibraries(pom.importRuntimeAndTestDependencies().resolve().withTransitivity().asFile());
        Arrays.stream(new File("src/main/resources").listFiles()).forEach(war::addAsResource);
        Arrays.stream(new File("src/test/resources").listFiles()).forEach(war::addAsResource);
//        System.out.println(war.toString(true));
        return war;
    }
}

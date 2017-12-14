package com.github.wiro34.hairspray.arquillian;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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
}

package com.fyle.test.module;

import com.fyle.test.dao.BankDao;
import com.fyle.test.service.BankService;
import com.fyle.test.service.impl.BankServiceImpl;
import com.google.inject.AbstractModule;


public class BankAssignmentModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BankService.class).to(BankServiceImpl.class).asEagerSingleton();
        bind(BankDao.class).asEagerSingleton();
    }
}


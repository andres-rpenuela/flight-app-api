package com.tokioschool.flightapp.base.transaccions.service;

import java.io.IOException;

public interface BaseTransactionalService {

    void nonTransactionalWithOneCommit();
    void nonTransactionalWithMultipleCommit();

    void transactionalWithOneCommit();
    void transactionalWithMultipleCommit();


    void nonTransactionalWithException();

    void transactionalWithException();

    void transactionalWithCheckedCommit() throws IOException;

    void transactionalWithCheckedRollback() throws IOException;
}

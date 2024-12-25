package com.tokioschool.flightapp.batch.filter;

@FunctionalInterface
public interface FilterItem<T,R> {
    R filter(T item);
}

package com.alura.spring_desafio4;

@FunctionalInterface
public interface  OperaSoma<T,R> {

    public R operacao(T a, T b);
}

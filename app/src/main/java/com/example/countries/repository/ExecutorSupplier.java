package com.example.countries.repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorSupplier {

    private static final ExecutorService instance = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //constructor
    private ExecutorSupplier() {
    }

    public static ExecutorService getInstance(){
        return instance;
    }
}

//executor para que no se abran mas de 4 hilos.limita la cantidad de hilos a maximo 4.

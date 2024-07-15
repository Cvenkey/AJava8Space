package com.ajava8.space.threads;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CalculateService calculateService = new CalculateService();
        AtomicInteger n = new AtomicInteger(0);
        AtomicInteger r = new AtomicInteger(0);
        n.set(10);r.set(3);
        Future<Long> future = executorService.submit(() -> calculateService.ncr(n.get(), r.get()));

        if (!future.isDone()) {
            System.out.println("Future not completed waiting" + LocalDateTime.now());
        }

        System.out.println("Initializing n and c");


        try {
            long result = future.get();
            System.out.println("Result @ "+LocalDateTime.now()+" :"+ result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        // Shutdown the executor
        executorService.shutdown();


        if (future != null) {
            
        }

    }
}

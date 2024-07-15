package com.ajava8.space.threads;

import java.util.concurrent.*;

public class ScheduledExecutorCallable {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService ses = Executors.newFixedThreadPool(1);
        Callable<Integer> task2 = () -> 10;
        task1();
        Future<Integer> schedule = ses.submit(task2);
        task3();
        System.out.println(schedule.get());
        System.out.println("shutdown!");
        ses.shutdown();

    }

    public static void task1() {
        System.out.println("Running task1...");
    }
    public static void task3() {
        System.out.println("Running task3...");
    }
}
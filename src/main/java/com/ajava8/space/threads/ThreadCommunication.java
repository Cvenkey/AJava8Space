package com.ajava8.space.threads;

import java.util.ArrayList;
import java.util.List;

public class ThreadCommunication {
    private final List<Integer> sharedList = new ArrayList<>();
    private final int MAX_SIZE = 10;
    
    public static void main(String[] args) {
        ThreadCommunication communication = new ThreadCommunication();
        
        Thread producer = new Thread(communication.new Producer());
        Thread consumer = new Thread(communication.new Consumer());
        
        producer.start();
        consumer.start();
    }
    
    class Producer implements Runnable {
        @Override
        public void run() {
            int value = 0;
            while (true) {
                synchronized (sharedList) {
                    while (sharedList.size() == MAX_SIZE) {
                        try {
                            sharedList.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Adding: " + value);
                    sharedList.add(value++);
                    sharedList.notify();
                    
                    try {

                        Thread.sleep(100); // Simulate time-consuming task
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (sharedList) {
                    while (sharedList.isEmpty()) {
                        try {
                            sharedList.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                    int value = sharedList.remove(0);
                    System.out.println("Removed: " + value);
                    sharedList.notify();
                    
                    try {
                        Thread.sleep(100); // Simulate time-consuming task
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

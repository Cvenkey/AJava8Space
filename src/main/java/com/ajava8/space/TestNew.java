package com.ajava8.space;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestNew {

    public static void main(String[] args) {
        LinkedList<Integer> resource = new LinkedList<>();
        Thread producer = new Thread(new Consumer(resource));
        Thread consumer = new Thread(new Producer(resource));
        producer.start();
        consumer.start();
    }

}

class Producer implements Runnable {
    private final int MAX_SIZE = 10;
    LinkedList<Integer> resource;

    Producer(LinkedList<Integer> resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for(;resource.size()==MAX_SIZE;){
                    System.out.println("All elements added to resource @" + LocalDateTime.now());
                    resource.wait();
                }
                resource.addAll(IntStream.rangeClosed(1,10).boxed().collect(Collectors.toList()));
                resource.notify();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error @ Producer-" + e.getMessage());
            }
        }
    }
}

class Consumer implements Runnable {
    LinkedList<Integer> resource;
    StringJoiner elements = null;

    Consumer(LinkedList<Integer> resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            elements = new StringJoiner(",", "[", "]");
            try {
                boolean isEmpty = false;
                for (; !isEmpty; ) {
                    Integer element =  resource.removeFirst();
                    System.out.println("Removed:"+element);
                    elements.add(element.toString());

                    if (resource.isEmpty()) {
                        isEmpty = true;
                        System.out.println("All elements removed from resource @" + LocalDateTime.now()+" "+elements);
                        resource.wait();
                    }
                }
                resource.notify();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error @ Consumer-" + e.getMessage());
            }
        }
    }
}
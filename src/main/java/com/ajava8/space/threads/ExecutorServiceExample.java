package com.ajava8.space.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ExecutorServiceExample {

    List<String> list = new ArrayList<>();
    public static void main(String[] args) {
        ExecutorServiceExample executorServiceExample = new ExecutorServiceExample();
//        executorServiceExample.startLoadAndEmpty();
//        executorServiceExample.displayListSize();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        executorServiceExample.loadList();
        Callable callable = ()->executorServiceExample.emptyList();
        //ses.submit(runnable,0, TimeUnit.MICROSECONDS);
        ses.schedule(callable,5,TimeUnit.SECONDS);
        executorServiceExample.displayListSize();
        ses.shutdown();
    }

    public void startLoadAndEmpty(){

    }

    public String displayListSize(){
        return  "List size before removal: "+list.size();
    }
    public void loadList(){
        AtomicInteger i = new AtomicInteger();
        list = Stream.generate(()->"element"+ i.getAndIncrement())
                .limit(1000).collect(Collectors.toList());
        System.out.println(list.size()+" Elements Added");
    }
    public String emptyList(){
        IntStream intStream = IntStream.rangeClosed(0,999);
        intStream.forEach(index->{
            System.out.println("Removing element"+index);
            list.remove("element"+index);
        });
        return displayListSize();

    }
}

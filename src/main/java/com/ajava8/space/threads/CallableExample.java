package com.ajava8.space.threads;

import java.io.Serializable;
import java.rmi.Remote;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Map<Character, Long>  occurrences = (Map<Character, Long>)
                executorService.submit(new CharacterCounter("Hello World")).get();
        executorService.shutdown();
        occurrences.entrySet().forEach(entry->System.out.println(entry.getKey()+" "+entry.getValue()));

        ThreadPoolExecutor t = new ScheduledThreadPoolExecutor(10);
        new ZeroMover(new int[]{0,1,2,0,3,4,0}).run();

        ScheduledExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
        long mills = new Date().getTime()/1000;
        executorService1.schedule(Test::someTask,10,TimeUnit.SECONDS);
        System.out.println("This is last line but appear before sometask method:"+ LocalDateTime.now());

    }
}

class Test {
    static void someTask(){
        System.out.println("this is someTask: executed after 60 sec."+LocalDateTime.now());
    }


}

class CharacterCounter implements Callable {
    String str = null;
    public CharacterCounter(String str){
        this.str =str;
    }
    @Override
    public Map<Character, Long> call() {
        Stream<Character> chars = str.chars().mapToObj(ch -> (char)ch);
        Map<Character, Long> occurrences =  chars.
                collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        return occurrences;
    }
}

class ZeroMover implements Runnable{
    int[] integers;
    ZeroMover (int[] integers){
        this.integers = integers;
    }
    @Override
    public void run() {
        int j=0;
        for(int i=0;i<integers.length;i++){
            if(integers[i]==0)
                continue;
            integers[j] = integers[i];
            j++;
        }
        for(;j<integers.length;j++)
            integers[j] = 0;
       System.out.println(Arrays.toString(integers));
    }
}
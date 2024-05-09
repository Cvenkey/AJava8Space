package com.ajava8.space.threads;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Arrays;
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
        new ZeroMover(new int[]{0, 3, 0, 2, 8, 0}).run();
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
        Long noOfZeros = Arrays.stream(integers).filter(i->i==0).count();
        List<Integer> integers = Arrays.stream(this.integers).filter(i->i!=0).boxed().collect(Collectors.toList());
        for(int i = 0; i<noOfZeros; i++){
            integers.add(0);
        }
        int[] modifiedArray = new int[this.integers.length];
        for(int i =0;i<=integers.size()-1;i++)
            modifiedArray[i] =integers.get(i);
       System.out.println(Arrays.toString(modifiedArray));
    }
}
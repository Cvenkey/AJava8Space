package com.ajava8.space.strops;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringOperations {
    public static void main(String[] args) {
        StringOperations stringOperations = new StringOperations();
        stringOperations.miscellaneousOps();
        stringOperations.countEachCharacter();
        stringOperations.removeDuplicateChars("Hello World");
        stringOperations.findRepeatedAndNonRepeatedChars();
        System.out.println("No. of times repeated:"+stringOperations.getCount('l'));
        System.out.println("No. of times String repeated:"+stringOperations.getStringCount("Hello"));


    }

    public void findRepeatedAndNonRepeatedChars(){
        String str = "Hello World";
        Map<Character,Long> charMap = str.chars().mapToObj(ch->(char)ch).collect
                (Collectors.groupingBy(Function.identity(),Collectors.counting()));
        StringJoiner repeatedChars = new StringJoiner(",","[","]");
        StringJoiner nonRepeatedChars = new StringJoiner(",","[","]");

        AtomicInteger count = new AtomicInteger();
        charMap.keySet().forEach(k->{
            if(charMap.get(k)>1) {
                repeatedChars.add(String.valueOf(k));
                count.getAndIncrement();
            }
            else
                nonRepeatedChars.add(String.valueOf(k));
        });
        System.out.println("No of Duplicates:"+count.get());
        System.out.println("RepeatedChars:"+repeatedChars);
        System.out.println("Non RepeatedChars:"+nonRepeatedChars);
    }

    private void miscellaneousOps() {
        Integer[] intArray = new Integer[]{2,5,9,1,7};
        int sum = Arrays.stream(intArray).filter(i->i%2==0).mapToInt(i->i).sum();
        System.out.println("Sum is:"+sum);

        //find length of each string
        System.out.println("Length of Each String");
        String [] strs = new String[] {"lions", "tigers", "bears","elephants"};
        Stream.of(strs).map(str-> str.length())
                .collect(Collectors.toList()).forEach(l->System.out.print(" "+l));







    }

    private void countEachCharacter(){
        String str = "Hello World";
        Map<Character,Long> charMap = str.chars().mapToObj(ch->(char)ch).collect
                (Collectors.groupingBy(Function.identity(),Collectors.counting()));
        charMap.entrySet().forEach(e->System.out.println(e.getKey()+" "+e.getValue()));
    }

    private long getCount(char character){
        String str = "Hello World";
        IntPredicate isTargetedChar = ch->ch == character;
        return str.chars().filter(isTargetedChar).count();
    }

    private long getStringCount(String given){
        String str = "Hello World Hello";
        return Arrays.stream(str.split(" ")).filter(predicateTest(given)).count();
    }


    private void reverseString(){
        String countChars = "Hello World";
        IntStream intStream = countChars.chars().map(ch -> (char) ch);
        Stream<Integer> integerStream = intStream.mapToObj(a -> (int) a);
        List<Integer> collect = integerStream.collect(Collectors.toList());
        Collections.reverse(collect);
        collect.forEach(i-> System.out.println((char)i.intValue()));
    }

    public static String removeDuplicateChars(String input) {
        //With Stream
        String str = "Hello World";
        IntStream stream = str.chars();
        List<Character> list = stream.mapToObj(ch->(char)ch).distinct().collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        list.stream().forEach(ch->builder.append(ch));
        System.out.println("Resultant String:"+builder);

        if (input == null || input.isEmpty()) {
            return input;
        }

        // Boolean array to keep track of seen characters
        boolean[] seen = new boolean[256]; // Assumes ASCII characters
        StringBuilder result = new StringBuilder();

        // Iterate over the characters in the input string
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!seen[c]) {
                seen[c] = true; // Mark character as seen
                result.append(c); // Append character to result
            }
        }
        System.out.println(result);
        return result.toString();
    }

    Predicate<? super String> predicateTest(Object targetObject) {
        return obj -> obj.equals(targetObject);
    }
}

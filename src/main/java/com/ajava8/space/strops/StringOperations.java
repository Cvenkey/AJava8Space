package com.ajava8.space.strops;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringOperations {
    public static void main(String[] args) {
        StringOperations stringOperations = new StringOperations();
        stringOperations.miscellaneousOps();
    }
    private void miscellaneousOps(){
        String countChars = "Hello World";
        IntStream intStream = countChars.chars().map(ch -> (char) ch);
        Stream<Integer> integerStream = intStream.mapToObj(a -> (int) a);
        List<Integer> collect = integerStream.collect(Collectors.toList());
        Collections.reverse(collect);
        collect.forEach(i-> System.out.println((char)i.intValue()));
    }
}

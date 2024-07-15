package com.ajava8.space.core;


import java.security.cert.PKIXRevocationChecker;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestNew {

    public void method(int s1){
        System.out.println("this is int type"+s1);
    }
    public void method(Object o1){
        System.out.println("this is Object class content-"+o1+"Type is:"+o1.getClass());
    }
    public void method(Integer s1){
        System.out.println("this is Integer class content-"+s1+"Type is:"+s1.getClass());
    }
    public void method(String s1){
        System.out.println("this is String class content-"+s1+"Type is:"+s1.getClass());
    }




    public static void main(String[] args) {
        TestNew tn = new TestNew();
        tn.method(args[0]);
        int i = 14;
        tn.method(i);
        Integer ig = new Integer(123);
        tn.method(ig);
        Object obj = "Object Contents";
        tn.method(obj);
        String str1 ="name";
        tn.method(str1);
        tn.method(tn);
        Object intObj = ig;
        tn.method(intObj);

        Stream<String> strs = Stream.of("one","Two","Three","Four");
       // strs.map(s->s.concat("T")).collect(Collectors.joining(","));
       // strs.sorted(Comparator.comparingInt(s->s.length())).forEach(System.out::println);
        strs.map(s->new StringBuilder(s).reverse()).collect(Collectors.toList()).forEach(System.out::print);

        String str = "sagrra";
        AtomicBoolean isPrinted = new AtomicBoolean();
        isPrinted.set(Boolean.FALSE);
        List<Character> characters = new ArrayList<>();
        Integer integer = new Integer(0);
        int j =0;
        str.chars().mapToObj(ch->(char)ch).forEach(eachChar-> {

            boolean flag = characters.contains(eachChar);
            if (flag == Boolean.TRUE) {
                if (isPrinted.get()==Boolean.FALSE) {
                    isPrinted.set(Boolean.TRUE);
                    System.out.println("First Duplicate Char:" + eachChar+" "+integer+" "+j);
                }
            }
            characters.add(eachChar);
        });

        System.out.println("Squires Sum is: "+calculateSquires(4));
    }

    static long result = 0;
    //4*4+3*3+2*2+1*1
    static long calculateSquires(int number){
        result = result+(number * number);
        return number == 1 ? result: calculateSquires(--number);
    }

}

package com.ajava8.space.core;

import java.nio.file.OpenOption;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

public class OptionalExp {
    public static void main(String[] args) {
        Character ch = 'c';
        String str = "SomeThing";

        //of Method
        Optional<String>  optStr = Optional.of(str);
        Optional<Character> opt =optStr.get().chars().mapToObj(i->(char)i)
                .filter(c->c.charValue()==ch).findAny();

        //orElse and isPresent Method
        Character t = opt.isPresent() ? opt.get() : opt.orElse('T');
        System.out.println("orElse Method:"+t);

        //orMethod
        Supplier<Optional<Character>> characterSupplier = ()->Optional.of(someMethod('T'));
        Character ut = opt.isPresent() ? opt.get() : opt.or(characterSupplier).get();
        System.out.println("orMethod Test:" +ut);

        //empty method
        optStr  = optStr.get().equals(new StringBuilder(str).reverse()) ?
                Optional.of("Given String is palindrome") : Optional.empty();
        try {
            System.out.println("Palindrome Result:" + optStr.get());
        }catch (NoSuchElementException nsee){
            System.out.println("Caught NoSuchElementException:"+nsee.getMessage());
            //orElseGet method
            str = optStr.isPresent() ? optStr.get() : optStr.orElseGet(()->"Given String is not palindrome");
        }
        System.out.println("orElseGet Result:"+str);

        //ofNullable method
        str = null;
        Optional<String>  emptyOpt = Optional.ofNullable(str);
        System.out.println("ofNullable Test:"+emptyOpt.isPresent());

        //ifPresentOrElse method we can avoid if else condition with this
        emptyOpt.ifPresentOrElse(
                (value) -> System.out.println("Value is present, its: " + value),
                () -> System.out.println("Value is empty"));

        //ifPresentOrElse -- calling someMethod
        opt = Optional.ofNullable('T');
        opt.ifPresentOrElse(
                (val) -> someMethod(String.valueOf(val)),
                () -> someMethod());

        System.out.println("MultiOptional Test:"+str);
    }

    private static void someMethod(String value) {
        System.out.println("the value is not empty :"+value);
    }
    private static void someMethod(){
        System.out.println("this is empty value");
    }
    private static char someMethod(Character ch){
        return String.valueOf(ch).toLowerCase().charAt(0);
    }
}

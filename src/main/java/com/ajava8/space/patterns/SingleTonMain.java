package com.ajava8.space.patterns;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
//https://codepumpkin.com/breaking-singleton-using-reflection-and-enum-singleton/

public class SingleTonMain {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        SingleTon instance1 = SingleTon.singleTonObject;
        System.out.println("instance1 hashCode = " + instance1.hashCode());
        //Normal check
        SingleTon instance2 = SingleTon.singleTonObject;
        System.out.println("instance2 hashCode = " + instance2.hashCode());

        //Using cloning
        SingleTon cloneCopy= (SingleTon) instance2.clone();
        System.out.println("cloneCopy hashCode = " + cloneCopy.hashCode());

        // Below code will change constructor access level from private to public
        Constructor constructor = SingleTon.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        //Creating second instance but throws error
        SingleTon constructorCopy = (SingleTon) constructor.newInstance();
        System.out.println("constructorCopy hashCode = " + constructorCopy.hashCode());
    }
}

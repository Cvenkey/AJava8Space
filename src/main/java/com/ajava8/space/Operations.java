package com.ajava8.space;

public interface Operations {
    default void  fibonacciSequence(int limit){
        int a=0;
        int b=1;
        int f;
        int[] feb = new int[limit+2];
        feb[0] = a;
        feb[1] = b;
        System.out.print(" "+feb[0]+" "+feb[1]);
        for(int i=0;i<limit;i++){
            f = a+b;
            feb[i+2] = f;
            System.out.print(" "+feb[i+2]);
            a=b;
            b=f;
        }
    }
}

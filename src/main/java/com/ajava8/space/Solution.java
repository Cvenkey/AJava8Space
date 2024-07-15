package com.ajava8.space;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()){
            String IP = in.next();
            System.out.println();
            System.out.print(IP.matches(new com.ajava8.space.MyRegex().pattern));
        }
        in.close();
    }
}

class MyRegex{
    public String pattern = "^((0|[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}" +
            "(0|[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-5])$\n";

}

package com.ajava8.space.threads;

public class CalculateService{

    public static void main(String[] args) {
        CalculateService cs = new CalculateService();
        System.out.println(  cs.factorial(3));
    }
        long squiresSum = 0;
        long factorial = 0;
        float ncr = 0;
        public long calculateSquires(int number){
            squiresSum = squiresSum +(number * number);
            return number == 1 ? squiresSum : calculateSquires(--number);
        }

        public long factorial(int number){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            factorial = number;
            return number == 1 ? factorial : (factorial*factorial(--number));
        }

        public long ncr(int n, int r){
            System.out.println("parameters :"+n+" "+r);
            return factorial(n)/((factorial(n-r))*(factorial(r)));
        }
    }
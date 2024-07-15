package com.ajava8.space.core;

public class ArrayFrequency {

    public static void main(String[] args) {
        int array[] = {1,2,2,3,4,4,4,5};
        displayFrequency(array);
    }

    private static void displayFrequency(int[] array) {
        int size = array.length;
        boolean visited[] = new boolean[size];
        int frequency[] = new int[size];

        for(int i=0;i<size;i++){
            if(visited[i] == true)
                continue;

            int count = 1;
            for(int j=i+1;j<size;j++){
                if(array[i] == array[j]){
                    count++;
                    visited[j] = true;
                }
            }

            frequency[i] = count;
//            System.out.println("Visited"+Arrays.toString(visited));
//            System.out.println("Frequency"+Arrays.toString(frequency));
        }
        for(int i=0;i<size;i++){
            if(visited[i] == false)
                System.out.println("Element "+array[i]+" time(s)"+ frequency[i]);
        }
    }
}

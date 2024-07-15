package com.ajava8.space.core;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ArrayOperations {
    public static void main(String[] args) {
        //ArrayOperations.arraySort();
        int a[]={3,1,0,-1,2,7,3};
        int[] zm = {0,1,2,0,3,4,0,4};
        int[] r = {1, 2, 2, 3, 4, 4, 5, 6, 6 };
        int[] a1= {2,7,4,9};
        int[] a2= {3,7,8,1,9}; //1 2 3 4 7 8 9

        ArrayOperations.sort(a);
        ArrayOperations.bubbleSort(a);
        ArrayOperations.zeroMover(zm);
        ArrayOperations.dupCount(zm);
        ArrayOperations.removeDuplicates(r);
        combineArrayWithOutDuplicates(a1,a2);
    }
    static void combineArrayWithOutDuplicates(int[] a1, int[] a2){
        int duplicates = 0;
        for(int i=0;i<a1.length;i++){
            for (int j = 0; j < a2.length; j++) {
                if(a1[i]==a2[j])
                    duplicates++;
            }
        }
        int[] r = new int[a1.length+a2.length-duplicates];
        int rIndex = 0;
        for(int i=0;i<a1.length;i++){
            if(!isElementExist(r,a1[i])){
               r[rIndex] = a1[i];
               rIndex++;
            }
        }

        for(int i=0;i<a2.length;i++){
            if(!isElementExist(r,a2[i])){
                r[rIndex] = a2[i];
                rIndex++;
            }
        }

        System.out.println("Combined Array without dups: "+Arrays.toString(r));
    }
    static boolean isElementExist(int[] array,int element){
        Boolean isExist = Boolean.FALSE;
        for(int i : array){
            if(i == element) {
                isExist = Boolean.TRUE;
                break;
            }
        }
        return isExist;
    }



    private static int[] removeDuplicates(int[] sortedArray) {
        if(sortedArray.length<1)
            return sortedArray;

        int j = 0;
        for(int i=0;i<sortedArray.length-1;i++){
            if(sortedArray[i]!=sortedArray[i+1]){
                sortedArray[j] = sortedArray[i];
                j++;
            }
            System.out.println(i+" "+j+" "+Arrays.toString(sortedArray));
        }
        sortedArray[j++] = sortedArray[sortedArray.length-1];
        System.out.println(Arrays.toString(sortedArray));
        return sortedArray;
    }

    public static void zeroMover(int[] array){
        int newArray[] = new int[array.length];
        int j=0;
        for(int i=0;i<array.length;i++){
            if(array[i]==0)
                continue;

            newArray[j] = array[i];
            j++;
        }
        System.out.println("Modified Array"+Arrays.toString(newArray));
    }

    public static void arraySort(){
        int [] a1 = {2,3,4, -1};
        int [] a2 = {3,4};

        int a12 [] = ArrayOperations.combineArrays(a1,a2);
        int dupsCount = ArrayOperations.dupCount(a12);

        System.out.println("No.of duplicates:"+dupsCount);
        int a13[] =new int[a1.length+a2.length-dupsCount];

        int insertCount = 0;
        for(int i=0;i<a12.length;i++) {
            boolean canInsert = Boolean.TRUE;

            for (int j = 0; j <insertCount; j++) {
                if (a13[j]==a12[i]) {
                  canInsert = Boolean.FALSE;
                }
            }
            if(canInsert) {
                a13[insertCount] = a12[i];
                insertCount++;
            }
        }
        System.out.println(Arrays.toString(a1));
        System.out.println(Arrays.toString(a2));
        System.out.println(Arrays.toString(sort(a13)));
    }

    private static int[] combineArrays(int a[], int b[]) {
        int r[] = new int[a.length + b.length];
        int i = 0;
        for (int e : a) {
            r[i] = e;
            i++;
        }
        for (int e : b) {
            r[i] = e;
            i++;
        }
        return r;
    }

    //To get duplicate elements count
    private static int
    dupCount(int a[]){
        int duplicates = 0;
        boolean[] visited = new boolean[a.length];

        for(int i=0;i< a.length;i++){
            if(visited[i] == true)
                continue;

            int count =1;
            for(int j=i+1;j<a.length;j++) {
                if (a[i] == a[j]) {
                  count++;
                  visited[j] =true;
                }
            }
            if(count>1)
                duplicates++;
        }
        System.out.println("No of Duplicates: "+duplicates);
        return duplicates;
    }
    private static int[]  sort(int[] a1) {
        int times=0;
        for(int i = 0; i< a1.length-1; i++){
            times++;
            if(a1[i]> a1[i+1]){
                int temp = a1[i];
                a1[i] = a1[i+1];
                a1[i+1] = temp;
                i=-1;
             }
        }
        System.out.println("No.of iterations:"+times);
        return a1;


    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        int times=0;
        for (int i = 0; i < n - 1; i++) {
            times++;
            for (int j = 0; j < n - i - 1; j++) {
                times++;
                System.out.println("i="+i+" "+"j="+j);
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j + 1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("No.of iterations bubble sort:"+times);
    }
}

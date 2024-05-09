package com.ajava8.space.threads;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadExample {
    private AtomicBoolean callingActive = new AtomicBoolean(false);
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    public void simulateActivities() {

        Runnable playSong = () -> {
            while (atomicInteger.get()>0 && atomicInteger.get()<10) {
                if (!callingActive.get()) {
                    System.out.println("Playing song...");
                    try {
                        Thread.sleep(1000); // Simulate time taken to play a song
                        atomicInteger.set(new Integer(atomicInteger.get()+1));
                        System.out.println("PlaySong Thread changed the value"+atomicInteger.get());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interruption status
                    }
                }else{
                    System.out.println("Song Interrupted @ "+new Date());
                }
            }
        };
        Runnable browseInternet = () -> {
            while (atomicInteger.get()>0 && atomicInteger.get()<10) {
                if (!callingActive.get()) {
                    System.out.println("Browsing Internet...");
                    try {
                        Thread.sleep(800); // Simulate time taken to browse
                        atomicInteger.set(new Integer(atomicInteger.get()+1));
                        System.out.println("BrowseInternet Thread changed the value:"+atomicInteger.get());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interruption status
                    }
                }else{
                    System.out.println("Browsing Interrupted @ "+new Date());
                }
            }
        };
        Runnable calling = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(5000); // Wait a bit before starting the call
                    callingActive.set(true);
                    System.out.println("Starting call"+ new Date());
                    Thread.sleep(6000); // Simulate call duration
                    atomicInteger.set(new Integer(0));
                    System.out.println("calling Thread reset the value:"+atomicInteger.get());
                    System.out.println("Call ended."+new Date());
                    callingActive.set(false);
                } catch (InterruptedException e) {
                    Thread.currentThread().isInterrupted();
                }
            }
        };

        Thread playSongThread = new Thread(playSong);
        Thread browseInternetThread = new Thread(browseInternet);
        Thread callingThread = new Thread(calling);

        playSongThread.start();
        browseInternetThread.start();
        callingThread.start();
    }

    public static void main(String[] args) {
        new MultiThreadExample().simulateActivities();
    }

}

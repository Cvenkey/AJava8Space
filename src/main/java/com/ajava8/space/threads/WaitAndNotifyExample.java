package com.ajava8.space.threads;

public class WaitAndNotifyExample {

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread playingSongThread = new Thread(new PlayingSong(sharedResource), "PlayingSong");
        Thread browsingInternetThread = new Thread(new BrowsingInternet(sharedResource), "BrowsingInternet");
        Thread callingThread = new Thread(new CallingThread(sharedResource), "CallingThread");

        sharedResource.startCalling();
        playingSongThread.start();
        browsingInternetThread.start();

        try {
            Thread.sleep(1000); // Let other threads start first
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callingThread.start();
    }
}

class SharedResource {
    private volatile boolean callingInProgress = false;

    public synchronized void startCalling() {
        callingInProgress = true;
        notifyAll();
    }

    public synchronized void finishCalling() {
        callingInProgress = false;
        notifyAll();
    }

    public synchronized boolean isCallingInProgress() {
        return callingInProgress;
    }

    public synchronized void waitForCallCompletion() throws InterruptedException {
        while (callingInProgress) {
            wait();
        }
    }
}

class PlayingSong implements Runnable {
    private final SharedResource sharedResource;

    public PlayingSong(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + " is playing a song.");
                Thread.sleep(1000);
                sharedResource.waitForCallCompletion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BrowsingInternet implements Runnable {
    private final SharedResource sharedResource;

    public BrowsingInternet(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + " is browsing the internet.");
                Thread.sleep(1000);
                sharedResource.waitForCallCompletion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CallingThread implements Runnable {
    private final SharedResource sharedResource;

    public CallingThread(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + " is making a call.");
                Thread.sleep(3000); // Simulate the call duration
                System.out.println(Thread.currentThread().getName() + " is ending a call.");
                sharedResource.finishCalling();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


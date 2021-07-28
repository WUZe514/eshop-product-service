package com.roncoo.eshop.product;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class SetTest {

    private Set<Integer> dimDataChangeMessageSet = Collections.synchronizedSet(new HashSet<>());
//    private Set<Integer> dimDataChangeMessageSet = new HashSet<>();

    Object lock = new Object();

    @Test
    public void test1() {
        dimDataChangeMessageSet.add(0);
        dimDataChangeMessageSet.add(1);
        dimDataChangeMessageSet.add(2);
        dimDataChangeMessageSet.add(3);
        dimDataChangeMessageSet.add(4);

        new SendThread().start();
        for (int i = 5; i < 100; i++) {
            try {
                synchronized (lock) {
                    dimDataChangeMessageSet.add(i);
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class SendThread extends Thread {

        @Override
        public void run() {
            while(true) {
                if(!dimDataChangeMessageSet.isEmpty()) {
                    synchronized (lock) {
                        for (int message : dimDataChangeMessageSet) {
                            System.out.println(message);
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        dimDataChangeMessageSet.clear();
                        System.out.println("清理。。。");
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        @Override
//        public void run() {
//            while (true) {
//                if (!dimDataChangeMessageSet.isEmpty()) {
//                    Iterator<Integer> iterator = dimDataChangeMessageSet.iterator();
//                    while (iterator.hasNext()) {
//                        int i = iterator.next();
//                        System.out.println(i);
//                        iterator.remove();
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    System.out.println("清理。。。");
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }
}

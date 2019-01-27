package io.github.zhaodj.thread;

import io.github.zhaodj.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolQueueConcurrence {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1);
        //BlockingQueue<Runnable> queue = new SynchronousQueue<>();
        ExecutorService consumer = new ThreadPoolExecutor(1, 100, 1, TimeUnit.MINUTES, queue);

        Thread producer1 = new Thread(() -> {
            while(true) {
                try {
                    consumer.submit(new Task("task1"));
                } catch (Exception e) {
                    Log.print("size: " + ((ThreadPoolExecutor) consumer).getPoolSize());
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread producer2 = new Thread(() -> {
            while (true) {
                try {
                    consumer.submit(new Task("task2"));
                } catch (Exception e) {
                    Log.print("size: " + ((ThreadPoolExecutor) consumer).getPoolSize());
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer1.start();
        producer2.start();
    }

    public static class Task implements Runnable {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

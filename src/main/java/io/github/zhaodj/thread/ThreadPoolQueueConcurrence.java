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
        ExecutorService consumer = new ThreadPoolExecutor(1, 10, 1, TimeUnit.MINUTES, queue);

        Thread producer1 = createProducer(consumer, "producer1");
        Thread producer2 = createProducer(consumer, "producer2");

        producer1.start();
        producer2.start();
    }

    private static Thread createProducer(ExecutorService consumer, String producerName){
       return new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                String name = producerName + ".task" + i;
                try {
                    consumer.submit(new Task(name));
                } catch (Exception e) {
                    Log.print("exception: " + name);
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static class Task implements Runnable {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Log.print("run: " + name);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

package io.github.zhaodj.thread;

import io.github.zhaodj.util.Log;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.*;

public class ThreadPoolQueueWaitTime {

    public static void main(String[] args) throws InterruptedException {
        //线程池消费
        BlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(500);
        RejectedExecutionHandler rejectedHandler = (r, e) -> {
            Log.print("rejected, pool size:" + e.getPoolSize() + ", task count:" + e.getTaskCount());
            r.run();
        };
        ExecutorService executor = new ThreadPoolExecutor(20, 40, 1, TimeUnit.MINUTES, queue, rejectedHandler);

        //任务结果集
        Queue<Future<CallResult>> futures = new ConcurrentLinkedQueue<>();

        //检查结果线程
        Thread checker = new Thread(() -> {
            while (true) {
                Iterator<Future<CallResult>> iter = futures.iterator();
                while (iter.hasNext()) {
                    Future<CallResult> future = iter.next();
                    if (future.isDone()) {
                        try {
                            CallResult result = future.get();
                            Log.print("id:" + result.getId()
                                    + ", task wait:" + result.waitCost()
                                    + ", task cost:" + result.actualCost()
                                    + ", actual cost:" + (System.currentTimeMillis() - result.getSubmitAt())
                                    + ", pool size:" + ((ThreadPoolExecutor) executor).getPoolSize());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iter.remove();
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        checker.start();

        //主线程生产任务
        int i = 0;
        while (true) {
            Log.print("submit task:" + i);
            Future<CallResult> future = executor.submit(new IdCallable(i, System.currentTimeMillis()));
            futures.add(future);
            i++;
            Thread.sleep(60);
        }
    }

    public static class CallResult {
        private int id;
        private long submitAt;
        private long executeAt;
        private long executeEnd;

        public CallResult(int id, long submitAt, long executeAt) {
            this.id = id;
            this.submitAt = submitAt;
            this.executeAt = executeAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getSubmitAt() {
            return submitAt;
        }

        public void setSubmitAt(long submitAt) {
            this.submitAt = submitAt;
        }

        public long getExecuteAt() {
            return executeAt;
        }

        public void setExecuteAt(long executeAt) {
            this.executeAt = executeAt;
        }

        public long getExecuteEnd() {
            return executeEnd;
        }

        public void setExecuteEnd(long executeEnd) {
            this.executeEnd = executeEnd;
        }

        public long waitCost() {
            return executeAt - submitAt;
        }

        public long actualCost() {
            return executeEnd - executeAt;
        }
    }

    public static class IdCallable implements Callable<CallResult> {
        private int id;
        private long start;

        public IdCallable(int id, long start) {
            this.id = id;
            this.start = start;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        @Override
        public CallResult call() throws Exception {
            CallResult res = new CallResult(id, start, System.currentTimeMillis());
            Thread.sleep(2000);
            res.setExecuteEnd(System.currentTimeMillis());
            return res;
        }
    }

}

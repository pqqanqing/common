package com.wjs.common.base.thread;

import java.util.concurrent.*;

/**
 * Created by panqingqing on 16/6/28.
 */
public class ThreadPool {

    private ExecutorService executorService;

    public ThreadPool() {
    }

    //自定义线程池
    public ThreadPool(int corePoolSize, int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 1L, TimeUnit.HOURS, new ArrayBlockingQueue(queueSize));
    }

    //缓存线程池
    public void newCachedThreadPool() {
        executorService = Executors.newCachedThreadPool();
    }

    //固定线程池
    public void newFixedThreadPool(int size) {
        executorService = Executors.newFixedThreadPool(size);
    }

    //单个线程池
    public void newSingleThreadExecutor() {
        executorService = Executors.newSingleThreadExecutor();
    }

    //时序线程池
    public void newScheduledThreadPool(int size) {
        executorService = Executors.newScheduledThreadPool(size);
    }

    //单个时序线程池
    public void newSingleThreadScheduledExecutor() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    //执行
    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    //停止
    public void shutdown() {
        executorService.shutdown();
    }

    //立刻停止
    public void shutdownNow() {
        executorService.shutdownNow();
    }

}

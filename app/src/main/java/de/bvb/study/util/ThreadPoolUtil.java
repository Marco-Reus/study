package de.bvb.study.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池
 */
public class ThreadPoolUtil {
    private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public static void execute(Runnable command) {
        pool.execute(command);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return pool.submit(task);
    }

    public static Future<?> submit(Runnable task) {
        return pool.submit(task);
    }

    public static <T> Future<T> submit(Runnable task, T result) {
        return pool.submit(task, result);
    }
}

package com.zdd.service.api.weixin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Xin
 * @date 2020/7/17 8:40 下午
 * @Content:
 */
@Slf4j
public class TaskTest {
    public static void main(String[] args) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = newThreadPoolTaskExecutor();
        for (int i =0;i<100;i++){
            int finalI = i;
            threadPoolTaskExecutor.execute(()-> get(finalI));
        }

    }

    public synchronized static void get(int i){
        log.info("线程名："+Thread.currentThread().getName()+"------"+i);
    }

    public static ThreadPoolTaskExecutor newThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(40000);
        executor.setThreadNamePrefix("zddTest--");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}

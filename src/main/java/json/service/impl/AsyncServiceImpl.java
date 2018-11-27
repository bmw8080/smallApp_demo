package json.service.impl;

import json.service.AsyncService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class AsyncServiceImpl implements AsyncService {
    private static Logger logger = LogManager.getLogger(AsyncServiceImpl.class.getName());

    @Override
    @Async("getAsyncExecutor")
    public void mainWait(CountDownLatch countDownLatch) {
        try {
            System.out.println("线程" + Thread.currentThread().getId() + "开始执行");
            for (int i=1;i<200;i++){
                Integer integer = new Integer(i);
                int l = integer.intValue();
                for (int x=1;x<10;x++){
                    Integer integerx = new Integer(x);
                    int j = integerx.intValue();
                }
            }
            System.out.println("线程" + Thread.currentThread().getId() + "执行结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override
    @Async("getAsyncExecutor")
    public void writeTxt(CountDownLatch countDownLatch) {
        try {
            System.out.println("线程" + Thread.currentThread().getId() + "开始执行");
            for (int i=1;i<100;i++){
                Integer integer = new Integer(i);
                int l = integer.intValue();
                for (int x=1;x<10;x++){
                    Integer integerx = new Integer(x);
                    int j = integerx.intValue();
                }
            }
            System.out.println("线程" + Thread.currentThread().getId() + "执行结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}

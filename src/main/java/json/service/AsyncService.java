package json.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CountDownLatch;

public interface AsyncService {
    @Async("getAsyncExecutor")
    void mainWait(CountDownLatch countDownLatch);

    void writeTxt(CountDownLatch countDownLatch);
}

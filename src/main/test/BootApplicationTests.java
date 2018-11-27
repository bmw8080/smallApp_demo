import json.App;
import json.service.AsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class BootApplicationTests {

    private final CountDownLatch countDownLatch = new CountDownLatch(10);

    @Autowired
    private AsyncService asyncService;

    @Test
    public void mainWait() {
        try {
            for (int i = 0; i < 10; i++) {
                asyncService.writeTxt(countDownLatch);
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

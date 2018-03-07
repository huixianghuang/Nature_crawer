package nature.util;

import java.util.concurrent.CountDownLatch;

/**
 * @author Prograk
 * 线程同步控制器
 */
public class CountDown {
    public static volatile CountDownLatch countDownLatch = new CountDownLatch(25);
}

package nature.util;

import nature.controller.pojo.ArticleAuthors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Prograk
 *
 * 返回crawerService和storeService通信的对象
 *
 * 存储可存储对象的阻塞队列，队列长度默认25
 */
public class BlockingQueueUtil {
    public static volatile boolean DONE = false;

    public static volatile BlockingQueue<ArticleAuthors> blockingQueue
            = new ArrayBlockingQueue<>(25);
}

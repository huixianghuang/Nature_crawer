package nature.controller.store;

import nature.ApplicationLauncher;
import nature.controller.pojo.ArticleAuthors;
import nature.util.BlockingQueueUtil;

/**
 * @author Prograk
 * 存储分发器
 *
 * 将生产者者构造的可存储对象分发给消费者进行存储
 */
public class StoreUtil implements Runnable{

    @Override
    public void run() {
        int i = 1;
        while (true){

            try {

                if (!BlockingQueueUtil.blockingQueue.isEmpty()) {
                    ArticleAuthors articleAuthors = BlockingQueueUtil.blockingQueue.take();
                    System.out.println("Number " + i++ + ": " + articleAuthors.getArticle());

                    if (articleAuthors.getAuthors() != null && articleAuthors.getAuthors().size() >= 1) {
                        ApplicationLauncher.storeService.submit(new Storable(articleAuthors));
                    }
                    else {
                        System.out.println("文章没有作者，不予以存储");
                    }

                }

                if (BlockingQueueUtil.DONE)
                    break;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        ApplicationLauncher.storeService.shutdown();
        System.out.println("种子存储完成");

        BlockingQueueUtil.DONE = false;

    }
}

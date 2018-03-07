package nature.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Prograk
 *
 * crawerService 生产者线程池
 * storeService  消费者线程池
 */
public class ExecService {

    private ExecutorService crawerService = Executors.newFixedThreadPool(5);
    private ExecutorService storeService = Executors.newFixedThreadPool(5);

    public ExecService(){
    }

    public ExecutorService getCrawerService() {
        return crawerService;
    }

    public ExecutorService getStoreService() {
        return storeService;
    }
}

package nature;

import nature.controller.crawer.SeedCrawer;
import nature.controller.store.StoreUtil;
import nature.util.BlockingQueueUtil;
import nature.util.ExecService;
import nature.util.SeedInit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Prograk
 * 程序入口
 */
@Configuration
@ComponentScan
public class ApplicationLauncher {

    public static ApplicationContext context;       //bean 容器
    public static ExecutorService crawerService;    //爬取线程池，充当生产者角色
    public static ExecutorService storeService;     //存储线程池，充当消费者角色

    public static void main(String [] args) throws IOException, SQLException {

        context = new ClassPathXmlApplicationContext("hikariConf.xml", "beanDefined.xml");

        //加载种子
        SeedInit seedInit = new SeedInit();
        seedInit.initHashMap();

        HashMap<Integer, String> hashMap = seedInit.getHashMap();

        //分发种子，爬取信息
        Iterator<Map.Entry<Integer, String>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (entry.getKey() > 57) {
                init(entry.getKey(), entry.getValue());
            }
        }

        System.out.println("全部爬取完成");

    }

    private static void init(int Index_id, String url){

        crawerService = context.getBean("executorService", ExecService.class).getCrawerService();
        storeService = context.getBean("executorService", ExecService.class).getStoreService();

        crawerService.execute(new SeedCrawer(url, Index_id));
        System.out.println("crawerService 运行中...");

        storeService.execute(new StoreUtil());
        System.out.println("storeService 运行中...");


        while (true){
            if (ApplicationLauncher.crawerService.isTerminated()){
                BlockingQueueUtil.DONE = true;
                break;
            }
        }
        System.out.println("种子爬取完成");


        //等待线程池所有任务完成，关闭后才进行下一步
        //当awaitTermination等待超过设定时间时，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。
        while (true){
            try {
                if (ApplicationLauncher.storeService.awaitTermination(1, TimeUnit.SECONDS)){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("storeService已关闭");
    }
}

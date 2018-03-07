package nature.controller.crawer;

import nature.ApplicationLauncher;
import nature.util.BlockingQueueUtil;
import nature.util.CountDown;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Prograk
 * 搜索页面搜索结果爬取，将每个爬取的论文链接构造成DetailCrawer进行论文详细信息爬取
 */
public class SeedCrawer implements Crawer, Runnable {

    protected String url;
    protected int Index_id;
    protected int page;
    protected int number;
    protected final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .build();

    private String selector = "#content > div > div > div > div.cleared.grid.grid-7.mq640-grid-12.mq640-last > div.pl20.mq640-pr20.hide-overflow > section > ol > li";
    private String pageSelector = "#content > div > div > div > div.cleared.grid.grid-7.mq640-grid-12.mq640-last > " +
            "div.pl20.mq640-pr20.hide-overflow > section > div > nav > ul > li:nth-last-child(2) > a";
    private String numberSelector = "#content > div > div > div > div.cleared.grid.grid-7.mq640-grid-12.mq640-last > " +
            "div:nth-child(1) > div > div.pin-left.pl10.mq875-hide > p > span:nth-last-child(1)";

    public SeedCrawer(){}
    public SeedCrawer(String url, int Index_id) {
        this.url = url;
        this.Index_id = Index_id;
    }

    @Override
    public void run() {
        crawer();
    }

    @Override
    public synchronized void crawer(){

        Document document = downLoad(url);

        String pageString = document.select(pageSelector).text();
        if (pageString != null && !pageString.equals(""))
            page = Integer.valueOf(pageString.substring(5));
        else
            page = 1;

        number = Integer.valueOf(document.select(numberSelector).text());
        System.out.println("本次爬取" + page + "页 " + number + "篇");
        System.out.println("种子为 " + url + "\n\n");

//        int i = 1;651
        int i = 164;

        if (Index_id > 58)
            i = 0;

        document = downLoad(getUrlPage(url, ++i));

        //将论文链接进行分发，按页数分发，一页25条
        while (i <= page) {

            Elements element = document.select(selector);
            for (Element e : element) {
                Elements e1 = e.select("li > div > h2 > a");

                //开始论文爬取
                String s1 = e1.attr("href").replaceFirst("http", "https");
                String s2 = e1.text();

                ApplicationLauncher.crawerService.execute(new DetialCrawer(s1, s2, Index_id));

            }

            System.out.println("第" + i + "页分发完毕...");

            if (i < page) {
                try {
                    CountDown.countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            document = downLoad(getUrlPage(url, ++i));

            CountDown.countDownLatch = new CountDownLatch(25);

        }

        ApplicationLauncher.crawerService.shutdown();


    }

    protected synchronized Document downLoad(final String url){

        final Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Mobile Safari/537.36")
                .build();

        Response response;
        Document document = null;
        try {
            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                System.err.println("failed " + url);
                response.close();
                //response 请求失败，重新请求
                // 无效则抛出异常，停止该线程
                response = client.newCall(request).execute();
                if (!response.isSuccessful()){
                    System.err.println("failed again " + url);
                }
            }

            document = Jsoup.parse(response.body().string());

            response.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }

    private synchronized String getUrlPage(final String url, final int page){
        return url + "&page=" + page;
    }

}

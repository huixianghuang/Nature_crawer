package nature.controller.crawer;

import nature.ApplicationLauncher;
import nature.controller.extract.DetailExtract;
import nature.controller.pojo.Article;
import nature.controller.pojo.ArticleAuthors;
import nature.controller.pojo.Author;
import nature.util.BlockingQueueUtil;
import nature.util.CountDown;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Prograk
 * 论文基本信息页面爬取
 */
public class DetialCrawer extends SeedCrawer{

    private String title;

    DetialCrawer() {

    }

    DetialCrawer(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public DetialCrawer(String url, String title, int Index_id) {
        this.url = url;
        this.title = title;
        this.Index_id = Index_id;
    }

    @Override
    public synchronized void crawer() {
        Document document = downLoad(url);
        Article article = new Article();
        article.setURL(url);
        article.setTitle(title);
        article.setIndex_id(Index_id);

        System.out.println("开始爬取论文信息");

        //download 作者和简介信息
        ArrayList<Author> authors = DetailExtract.extractMethod(document, article);

        //download 论文指标信息，ScoreCrawer
        String scoreUrl = url + "/metrics";
        new ScoreCrawer(scoreUrl, article).crawer();

        System.out.println("论文下载完成...");
        CountDown.countDownLatch.countDown();

        //爬取完，构造成可存储对象，丢给阻塞队列
        try {
            BlockingQueueUtil.blockingQueue.put(new ArticleAuthors(authors, article));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
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

            modifyUrl(response.toString());

            document = Jsoup.parse(response.body().string());

            response.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }

    private synchronized void modifyUrl(final String url){

        //提取 response 里的 URL
        String reg = "(?<=url=)\\S+(?=\\?)";

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(url);

        while(matcher.find()){
            //获取 字符串
            this.url = matcher.group();

        }

    }

}

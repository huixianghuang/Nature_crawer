package nature.controller.crawer;

import nature.controller.extract.ScoreExtract;
import nature.controller.pojo.Article;
import org.jsoup.nodes.Document;

/**
 *  @author Prograk
 *  论文指标页面爬取
 */
public class ScoreCrawer extends SeedCrawer {

    private Article article;

    ScoreCrawer() {

    }

    ScoreCrawer(String url, Article article) {
        this.url = url;
        this.article = article;
    }

    @Override
    public synchronized void crawer() {
        Document document = downLoad(url);
        ScoreExtract.extractMethod(document, article);
    }
}

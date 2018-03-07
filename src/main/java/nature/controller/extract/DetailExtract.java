package nature.controller.extract;

import nature.controller.pojo.Article;
import nature.controller.pojo.Author;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailExtract extends Extract {

//    private static String authorSelector = "#content > div > div > article > div:nth-child(1) > header > div > ul:nth-last-child(2) > li";
    private static String authorSelector = "li[itemprop='author']";
//    private static String informationSelector = "li > sup:nth-child(3) > span > meta:nth-child(1)";
    private static String informationSelector = "sup a[href^='#a']";
//    private static String nameSelector = "li > a > span";
    private static String nameSelector = "a[href^='#auth-']";

    private static String receivedSelector = "#content > div > div > article > div:nth-child(1) > " +
            "header > div > div > div.flex-box.grid.grid-6.last.mq480-grid-12 > div > dl > dd:nth-child(2) > time";
    private static String acceptedSelector = "#content > div > div > article > div:nth-child(1) > " +
            "header > div > div > div.flex-box.grid.grid-6.last.mq480-grid-12 > div > dl > dd:nth-child(4) > time";
    private static String publishedSelector = "#content > div > div > article > div:nth-child(1) > " +
            "header > div > div > div.flex-box.grid.grid-6.last.mq480-grid-12 > div > dl > dd:nth-child(6) > time";


    public static synchronized ArrayList<Author> extractMethod(final Document document, final Article article){

        if (document == null)
            return null;

        ArrayList<Author> authors = new ArrayList<>();

        Elements elements = document.select(authorSelector);
        for (Element e : elements){
            String name = e.select(nameSelector).text();
            String information = "";

            Elements e1 = e.select(informationSelector);

            for (Element e2 : e1) {
                String temp = e2.attr("href");
                if (temp != null && !temp.equals("")) {
                    information += document.select(temp).select("h3,h4").text() + "\n";
                }

            }

            authors.add(new Author(name, information));
        }

        try {
            String s1 = document.select(receivedSelector).attr("datetime");
            String s2 = document.select(acceptedSelector).attr("datetime");
            String s3 = document.select(publishedSelector).attr("datetime");

            if (s1 != null && !s1.equals(""))
                article.setRecevied(new SimpleDateFormat("yyyy-MM-dd").parse(s1));
            if (s2 != null && !s2.equals(""))
                article.setAccepted(new SimpleDateFormat("yyyy-MM-dd").parse(s2));
            if (s3 != null && !s3.equals(""))
                article.setPublished_online(new SimpleDateFormat("yyyy-MM-dd").parse(s3));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return authors;
    }

}

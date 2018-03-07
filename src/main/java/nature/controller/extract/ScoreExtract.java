package nature.controller.extract;

import nature.controller.pojo.Article;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreExtract extends Extract {

    private static String scienceSelector = "#content > div > div > article > div.grid.grid-12.last > " +
            "div.grid.grid-5.mq875-grid-12.just-mq875-last.standard-space-below > ul > " +
            "li:nth-child(1) > a > h2 > span > span";
    private static String crossRefSelector = "#content > div > div > article > div.grid.grid-12.last > " +
            "div.grid.grid-5.mq875-grid-12.just-mq875-last.standard-space-below > ul > " +
            "li:nth-child(2) > h2 > span > span";
    private static String scopusSelector = "#content > div > div > article > div.grid.grid-12.last > " +
            "div.grid.grid-5.mq875-grid-12.just-mq875-last.standard-space-below > ul > " +
            "li.grid.grid-4.last > a > h2 > span > span";
    private static String altmetricSelector = "#content > div > div > article > div.grid.grid-12.last > " +
            "div.grid.grid-6.grid-left-1.last.mq875-grid-12.just-mq875-last.mq875-kill-left.standard" +
            "-space-below > div.grid.grid-4.small-space-below > a > img";



    public static synchronized void extractMethod(final Document document, final Article article){

        if (document == null)
            return;

        String WebOfScience = document.select(scienceSelector).text();
        String CrossRef = document.select(crossRefSelector).text();
        String Scopus = document.select(scopusSelector).text();
        String Altmetric = null;

        String s = document.select(altmetricSelector).attr("src");
        String reg = "(?<=score=)\\d+(?=&)";

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(s);

        while(matcher.find()){
            //获取 字符串
            Altmetric = matcher.group();
        }

        if (WebOfScience != null && (!WebOfScience.contains("Data")) && (!WebOfScience.equals("")))
            article.setWeb_of_Science(Integer.valueOf(WebOfScience));

        if (CrossRef != null && (!CrossRef.contains("Data")) && (!CrossRef.equals("")))
            article.setCrossRef(Integer.valueOf(CrossRef));

        if (Scopus != null && (!Scopus.contains("Data")) && (!Scopus.equals("")))
            article.setScopus(Integer.valueOf(Scopus));

        if (Altmetric != null && (!Altmetric.contains("Data")) && (!Altmetric.equals("")))
            article.setAltmetric(Integer.valueOf(Altmetric));

    }

}

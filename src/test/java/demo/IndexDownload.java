//package demo;
//
//import com.zaxxer.hikari.HikariDataSource;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class IndexDownload {
//
//    private ApplicationContext context;
//
//    @Test
//    public void test() throws IOException {
//
//        context = new ClassPathXmlApplicationContext("hikariConf.xml", "beanDefined.xml");
//        HikariDataSource source = (HikariDataSource) context.getBean("dataSourceTest");
//        Connection connection = null;
//        try {
//            connection = source.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String url = "http://www.nature.com/siteindex/index.html";
//        String selector = "#back-to-top > div > div.grid.grid-8.mb20.mq640-grid-12.mq640-last";
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            System.out.println("failed....");
//            return;
//        }
//
//        Document document = Jsoup.parse(response.body().string());
//        Elements element = document.select(selector);
//
//        try {
//
//            //开启事务
//            connection.setAutoCommit(false);
//            PreparedStatement p = connection.prepareStatement("INSERT INTO nature_index(Index_name, Index_url) VALUES (?, ?)");
//            for (Element e : element) {
//                Elements e1 = e.select("div > div > ul > li");
//
//                for (Element e11 : e1) {
//                    Elements e2 = e11.select("li > a");
//                    System.out.println(e2.text() + " " + e2.attr("href"));
//
//                    p.setString(1, e2.text());
//                    p.setString(2, e2.attr("href"));
//
//                    p.execute();
//                }
//            }
//
//            //事务成功，提交事务
//            connection.commit();
//            connection.setAutoCommit(true);
//
//            p.close();
//            connection.close();
//            source.close();
//
//            System.out.println("successful !!!!");
//        }
//        catch (SQLException e){
//            try {
//                connection.rollback();
//                connection.setAutoCommit(true);
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//
//        }
//
//    }
//
//}

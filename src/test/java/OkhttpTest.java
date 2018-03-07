//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class OkhttpTest {
//
//    @Test
//    public void test() throws IOException {
//
//        String url = "https://www.nature.com/search?journal=jes&article_type=research";
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .addHeader("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Mobile Safari/537.36")
//                .url(url)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            System.out.println("failed....");
//
//            return;
//        }
//
//        System.out.println(response.toString());
////
////        Document document = Jsoup.parse(response.body().string());
////        Elements element = document.select("li[itemprop='author']"); //information
////        for(Element e : element){
////            System.out.println(
////                    e.select("a[href^='#auth-']").text() + ": " );
////            Elements element1 = e.select("sup a[href^='#a']");
////            for( Element e1 : element1){
////                Elements ee = document.select(e1.attr("href"));
////                Elements eee = ee.select("h3,h4");
////                System.out.println(eee.text());
////            }
////
////            System.out.println("============================\n\n");
////        }
//
//    }
//
//}

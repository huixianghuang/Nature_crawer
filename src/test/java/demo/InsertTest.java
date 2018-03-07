//package demo;
//
//import nature.controller.pojo.Article;
//import nature.controller.pojo.ArticleAuthors;
//import nature.controller.pojo.Author;
//import nature.controller.store.Storable;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//public class InsertTest {
//
//    private ArrayList<Author> authors = new ArrayList<>();
//    private Article article = new Article();
//
//    @Test
//    public void testM(){
//        setAuthors();
//        setArticle();
//
//        new Storable(new ArticleAuthors(authors, article)).run();
//
//
//    }
//
//    @Test
//    public void testtime(){
//
//        System.out.println(new java.sql.Date(new Date("Tue Nov 22 00:00:00 CST 2016").getTime()));
//    }
//
//    private void setAuthors(){
//        for (int i = 0; i < 10; i++){
//            Author author = new Author("Qicong Shen" + i,
//                    "National Key Laboratory of Medical Immunology & " +
//                            "Institute of Immunology, Second Military Medical University, Shanghai 200433, China");
//            authors.add(author);
//        }
//    }
//    private void setArticle(){
//        article.setURL("https://www.nature.com/articles/nature25434");
//        article.setTitle("Tet2 promotes pathogen infection-induced myelopoiesis through mRNA oxidation");
//        article.setIndex_id(3);
//        article.setRecevied(new Date("Tue Nov 22 00:00:00 CST 2016"));
//        article.setAccepted(new Date("Tue Nov 22 00:00:00 CST 2016"));
//        article.setPublished_online(new Date("Tue Nov 22 00:00:00 CST 2016"));
//        article.setWeb_of_Science(12);
//        article.setCrossRef(121);
//        article.setScopus(123);
//        article.setAltmetric(1243);
//
//    }
//}

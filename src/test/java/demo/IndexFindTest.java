//package demo;
//
//import com.zaxxer.hikari.HikariDataSource;
//import nature.util.SeedInit;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.function.Predicate;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class IndexFindTest {
//    private ApplicationContext context;
//
//    @Test
//    public void find() throws SQLException {
//        context = new ClassPathXmlApplicationContext("hikariConf.xml", "beanDefined.xml");
//        HikariDataSource source = (HikariDataSource) context.getBean("dataSourceTest");
//
//        Connection connection = source.getConnection();
//
//        String sql = "select ID, Index_url from nature_index";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        HashMap<Integer, String> map = new HashMap<>();
//
//        while (resultSet.next()){
//            int id = resultSet.getInt("ID");
//            String s = resultSet.getString("Index_url");
//            System.out.println(id + " " + s);
//            map.put(id, s);
//
//            String reg = "(?<=com/)\\S+[^(?=/)]";
//            Pattern pattern = Pattern.compile(reg);
//            Matcher matcher = pattern.matcher(s);
//
//            while (matcher.find()){
//                System.out.println(matcher.group());
//            }
//
//        }
//
//        preparedStatement.close();
//        connection.close();
//        source.close();
//
//    }
//
//    @Test
//    public void regTest() {
//
//        try {
//            SeedInit seedInit = new SeedInit();
//            seedInit.initHashMap();
//            System.out.println(seedInit.getHashMap().toString());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}

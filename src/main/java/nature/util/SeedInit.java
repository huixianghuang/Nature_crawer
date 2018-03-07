package nature.util;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeedInit {
    private ApplicationContext context;
    private HashMap<Integer, String> hashMap = new HashMap<>();

    public void initHashMap() throws SQLException {

        context = new ClassPathXmlApplicationContext("hikariConf.xml", "beanDefined.xml");
        HikariDataSource source = (HikariDataSource) context.getBean("dataSourceTest");

        Connection connection = source.getConnection();

        String sql = "select ID, Index_url from nature_index";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int id = resultSet.getInt("ID");
            String s = resultSet.getString("Index_url");

            String start = "https://www.nature.com/search?journal=";
            String end = "&article_type=research";

            String reg = "(?<=com/)\\S+[^(?=/)]";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                hashMap.put(id, start + matcher.group() + end);
            }


        }

        preparedStatement.close();
        connection.close();
        source.close();
    }
    public HashMap<Integer, String> getHashMap() {
        return hashMap;
    }
}

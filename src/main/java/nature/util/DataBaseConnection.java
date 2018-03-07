package nature.util;

import com.zaxxer.hikari.HikariDataSource;
import nature.ApplicationLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Prograk
 * 数据库连接资源分配类，负责给数据库每个操作分配Connection
 */
public class DataBaseConnection {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("hikariConf.xml", "beanDefined.xml");       //bean 容器

    private static volatile Connection connection;

    public static synchronized Connection getConnection() {
        try {
            HikariDataSource hikariDataSource = (HikariDataSource)context.getBean("dataSourceTest");

            connection = hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



}

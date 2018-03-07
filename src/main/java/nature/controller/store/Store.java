package nature.controller.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nature.controller.pojo.Article;
import nature.controller.pojo.Author;
import nature.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class Store {
    private ArrayList<Author> authors;
    private Article article;
    private ArrayList<Integer> authorsID = new ArrayList<>();
    private int articleID;
    private PreparedStatement preparedStatement;

    private String authorString = "insert into author " +
            "(Name, Information) values(?, ?)";
    private String articleString = "insert into article " +
            "(URL, Title, Index_id, Received, Accepted, Published_online, Web_of_Science, CrossRef, Scopus, Altmetric) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String authorArticleString = "insert into author_article" +
            "(author_id, article_id) values(?, ?)";
    private String getLastID = "select LAST_INSERT_ID() as LAST_ID";

    Store(ArrayList<Author> authors, Article article){
        this.authors = authors;
        this.article = article;
    }

    public synchronized void startStore() throws SQLException {
        insertArticle(article);
        insertAuthor(authors);
        updateAuthorArticle(authorsID, articleID);
    }
    //插入作者信息表
    public synchronized void insertAuthor(ArrayList<Author> authors) throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(authorString);

        for (Author author : authors){

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getInformation());

            preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement(getLastID);
            ResultSet resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                authorsID.add(Integer.valueOf(resultSet.getString("LAST_ID")));
            }
            preparedStatement1.close();

        }

        preparedStatement.close();
        connection.close();

    }

    //插入文章信息表
    public synchronized void insertArticle(Article article) throws SQLException {
        Connection connection = DataBaseConnection.getConnection();

        preparedStatement = connection.prepareStatement(articleString);

        preparedStatement.setString(1, article.getURL());
        preparedStatement.setString(2, article.getTitle());
        preparedStatement.setInt(3, article.getIndex_id());

        Date receviedDate = null;
        Date acceptedDate = null;
        Date publishedOnlineDate = null;
        if (article.getRecevied() != null)
            receviedDate = new Date(article.getRecevied().getTime());
        if (article.getAccepted() != null)
            acceptedDate = new Date(article.getAccepted().getTime());
        if (article.getPublished_online() != null)
            publishedOnlineDate = new Date(article.getPublished_online().getTime());

        preparedStatement.setDate(4, receviedDate);
        preparedStatement.setDate(5, acceptedDate);
        preparedStatement.setDate(6, publishedOnlineDate);

        preparedStatement.setInt(7, article.getWeb_of_Science());
        preparedStatement.setInt(8, article.getCrossRef());
        preparedStatement.setInt(9, article.getScopus());
        preparedStatement.setInt(10, article.getAltmetric());

        preparedStatement.execute();

        PreparedStatement preparedStatement1 = connection.prepareStatement(getLastID);
        ResultSet resultSet = preparedStatement1.executeQuery();
        while (resultSet.next()) {
            articleID = Integer.valueOf(resultSet.getString("LAST_ID"));
        }

        preparedStatement1.close();
        preparedStatement.close();
        connection.close();

    }

    //更新文章作者关系表
    public synchronized void updateAuthorArticle(ArrayList<Integer> authorsID, int articleID) throws SQLException {

        Connection connection = DataBaseConnection.getConnection();
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(authorArticleString);
        for (Integer authorID : authorsID){
            preparedStatement.setInt(1, authorID);
            preparedStatement.setInt(2, articleID);

            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        preparedStatement.close();
        connection.close();

    }


}

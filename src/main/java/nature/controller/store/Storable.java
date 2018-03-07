package nature.controller.store;

import nature.controller.pojo.Article;
import nature.controller.pojo.ArticleAuthors;
import nature.controller.pojo.Author;

import java.sql.SQLException;
import java.util.ArrayList;

public class Storable implements Runnable{
    private volatile ArticleAuthors articleAuthors;

    Storable(){}

    public Storable(ArticleAuthors articleAuthors){
        this.articleAuthors = articleAuthors;
    }

    public synchronized ArrayList<Author> getAuthors(){
        return articleAuthors.getAuthors();
    }

    public synchronized Article getArticle(){
        return articleAuthors.getArticle();
    }

    @Override
    public void run() {
        try {
            System.out.println("开始存储");
            new Store(getAuthors(), getArticle()).startStore();
            System.out.println("存储成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

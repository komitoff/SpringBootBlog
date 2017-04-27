package springBootBlog.service;

import springBootBlog.entity.Article;
import springBootBlog.entity.User;
import java.util.List;

public interface ArticleService {
    void saveAndFlush(Article article);
    void delete(Article article);
    Article findOne(int id);
    boolean exists(int id);
    Article createArticle(String title, String content, User user);
    List<Article> findAll();
    List<Article> lastFiveArticles();
}

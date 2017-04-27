package springBootBlog.service;

import springBootBlog.entity.Article;
import springBootBlog.entity.User;

public interface ArticleService {
    void saveAndFlush(Article article);
    void delete(Article article);
    Article findOne(int id);
    boolean exists(int id);
    Article create(String title, String content, User user);

}

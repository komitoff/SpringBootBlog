package springBootBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBootBlog.entity.Article;
import springBootBlog.entity.User;
import springBootBlog.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public void saveAndFlush(Article article) {
        this.articleRepository.saveAndFlush(article);
    }

    @Override
    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    @Override
    public Article findOne(int id) {
        return this.articleRepository.findOne(id);
    }

    @Override
    public boolean exists(int id) {
        return this.articleRepository.exists(id);
    }

    @Override
    public Article create(String title, String content, User user) {
        Article article = new Article(title, content, user);
        return article;
    }
}

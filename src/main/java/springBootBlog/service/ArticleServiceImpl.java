package springBootBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBootBlog.entity.Article;
import springBootBlog.entity.User;
import springBootBlog.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    public Article createArticle(String title, String content, User user) {
        Article article = new Article(title, content, user);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }

    @Override
    public List<Article> lastFiveArticles() {
        List<Article> latest5 = this.articleRepository
                .findAll()
                .stream()
                .sorted((x, y) -> y.getDateAdded().compareTo(x.getDateAdded()))
                .limit(5)
                .collect(Collectors.toList());
        return latest5;
    }
}

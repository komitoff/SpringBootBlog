package springBootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springBootBlog.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByIdOrderBydateAddedDesc();
}

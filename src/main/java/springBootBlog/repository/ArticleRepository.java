package springBootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springBootBlog.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}

package springBootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springBootBlog.entity.Article;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}

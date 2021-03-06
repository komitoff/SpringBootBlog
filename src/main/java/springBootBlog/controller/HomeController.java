package springBootBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springBootBlog.entity.Article;
import springBootBlog.entity.User;
import springBootBlog.repository.ArticleRepository;
import springBootBlog.repository.UserRepository;
import springBootBlog.service.ArticleService;
import springBootBlog.service.UserService;
import springBootBlog.service.UserServiceImpl;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        List<Article> articles = this.articleService.findAll();
        List<Article> lastFiveArticles = this.articleService.lastFiveArticles();

        model.addAttribute("lastFiveArticles", lastFiveArticles);
        model.addAttribute("articles", articles);
        model.addAttribute("view", "home/index");

        return "base-layout";
    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if(!this.articleService.exists(id)) {
            return "redirect:/";
        }

        if(!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
            User userEntity = userService.getCurrentUser();

            model.addAttribute("user", userEntity);
        }

        Article article = this.articleService.findOne(id);
        article.setViewedCount(article.getViewedCount() + 1);
        this.articleService.saveAndFlush(article);
        model.addAttribute("view", "article/details");
        model.addAttribute("article", article);
        return "base-layout";
    }

}

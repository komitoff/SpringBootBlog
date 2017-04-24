package springBootBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import springBootBlog.service.UserServiceImpl;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String index(Model model) {
        List<Article> articles = this.articleRepository.findAll();
        List<Article> latestFiveArticles = this.articleRepository.findByIdOrderBydateAddedDesc();
        model.addAttribute("view", "home/index");
        model.addAttribute("articles", articles);
        model.addAttribute("latestFiveArticles", latestFiveArticles);
        return "base-layout";
    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if(!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        if(!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
            User userEntity = userService.getCurrentUser();

            model.addAttribute("user", userEntity);
        }

        Article article = this.articleRepository.findOne(id);
        article.setViewedCount(article.getViewedCount() + 1);
        this.articleRepository.saveAndFlush(article);
        model.addAttribute("view", "article/details");
        model.addAttribute("article", article);
        return "base-layout";
    }

}

package springBootBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springBootBlog.bindingModel.ArticleBindingModel;
import springBootBlog.entity.Article;
import springBootBlog.entity.User;
import springBootBlog.repository.ArticleRepository;
import springBootBlog.repository.UserRepository;
import springBootBlog.service.ArticleService;
import springBootBlog.service.UserService;
import springBootBlog.service.UserServiceImpl;

import java.beans.Transient;

@Controller
public class ArticleController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;


    @GetMapping("article/create")
    @PreAuthorize("isAuthenticated()")
    public String create (Model model) {
        model.addAttribute("view", "article/create");

        return "base-layout";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/create")
    public String createProcess (ArticleBindingModel articleBindingModel) {

        User userEntity = userService.getCurrentUser();

        Article articleEntity = articleService.createArticle(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity
        );

        this.articleService.saveAndFlush(articleEntity);
        return ("redirect:/");
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if(!this.articleService.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleService.findOne(id);

        if (!isAdminOrAuthor(article)) {
            return "redirect:/article/" + id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/edit");

        return "base-layout";
    }

    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(
            @PathVariable Integer id,
            ArticleBindingModel articleBindingModel) {
        if(!this.articleService.exists(id)) {
            return "redirect:/";
        }
        Article article = this.articleService.findOne(id);

        if (!isAdminOrAuthor(article)) {
            return "redirect:/article/" + id;
        }
        article.setTitle(articleBindingModel.getTitle());
        article.setContent(articleBindingModel.getContent());
        this.articleService.saveAndFlush(article);
        return "redirect:/";
    }

    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Integer id, Model model) {
        if(!this.articleService.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleService.findOne(id);

        if (!isAdminOrAuthor(article)) {
            return "redirect:/article/"+id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");

        return "base-layout";
    }

    @PostMapping("/article/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if(!this.articleService.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleService.findOne(id);

        if (!isAdminOrAuthor(article)) {
            return "redirect:/article/" + id;
        }

        this.articleService.delete(article);
        return "redirect:/";
    }

    @Transient
    public boolean isAdminOrAuthor(Article article) {

        User userEntity = userService.getCurrentUser();

        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }
}

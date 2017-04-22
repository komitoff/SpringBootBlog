package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.UserRepository;
import softuniBlog.service.UserService;

import java.beans.Transient;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

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

        Article articleEntity = new Article(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity
        );

        this.articleRepository.saveAndFlush(articleEntity);
        return ("redirect:/");
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if(!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

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
        if(!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.findOne(id);

        if (!isAdminOrAuthor(article)) {
            return "redirect:/article/" + id;
        }
        article.setTitle(articleBindingModel.getTitle());
        article.setContent(articleBindingModel.getContent());
        this.articleRepository.saveAndFlush(article);
        return "redirect:/";
    }

    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Integer id, Model model) {
        if(!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if (!isAdminOrAuthor(article)) {
            return "redirect:/article/"+id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");

        return "base-layout";
    }

    @PostMapping("/article/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if(!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if (!isAdminOrAuthor(article)) {
            return "redirect:/article/" + id;
        }

        this.articleRepository.delete(article);
        return "redirect:/";
    }

    @Transient
    public boolean isAdminOrAuthor(Article article) {

        User userEntity = userService.getCurrentUser();

        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }
}

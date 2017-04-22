package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.entity.Article;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.UserRepository;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String index(Model model) {
        List<Article> articles = this.articleRepository.findAll();
        model.addAttribute("view", "home/index");
        model.addAttribute("articles", articles);
        return "base-layout";
    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Long id) {
        if(!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

//        if(!(SecurityContextHolder.getContext().getAuthentication()
//                instanceof AnonymousAuthenticationToken)) {
//            UserDetails user = (UserDetails) SecurityContextHolder
//                    .getContext()
//                    .getAuthentication()
//                    .getPrincipal();
//            User userEntity = this.userRepository.findByEmail(user.getUsername());
//
//            model.addAttribute("user", userEntity);
//        }

        Article article = this.articleRepository.findOne(id);
        article.setViewedCount(article.getViewedCount() + 1);
        this.articleRepository.saveAndFlush(article);
        model.addAttribute("view", "article/details");
        model.addAttribute("article", article);
        return "base-layout";
    }

}

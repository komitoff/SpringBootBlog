package springBootBlog.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springBootBlog.bindingModel.UserBindingModel;
import springBootBlog.entity.Role;
import springBootBlog.entity.User;
import springBootBlog.repository.RoleRepository;
import springBootBlog.repository.UserRepository;
import springBootBlog.service.UserServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel){

        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            return "redirect:/register";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword())
        );

        Role userRole = this.roleRepository.findByName("ROLE_USER");

        user.addRole(userRole);

        this.userRepository.saveAndFlush(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("view", "user/login");

        return "base-layout";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){
        User userEntity = userService.getCurrentUser();

        model.addAttribute("user", userEntity);
        model.addAttribute("view", "user/profile");

        return "base-layout";
    }

    @GetMapping("/user/users")
    public String showAllUsers(Model model) {
        List<User> users = this.userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("view","user/users");
        return "base-layout";
    }
}


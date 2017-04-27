package springBootBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springBootBlog.entity.User;
import springBootBlog.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    //Helper method for finding the current user
    public User getCurrentUser() {
        UserDetails user = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User userEntity = this.userRepository.findByEmail(user.getUsername());
        return userEntity;
    }

    @Override
    public User createUser(String email, String fullName, String password) {
        User user = new User(email, fullName, password);
        return user;
    }

    @Override
    public void saveAndFlush(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}

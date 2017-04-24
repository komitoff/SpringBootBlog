package springBootBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springBootBlog.entity.User;
import springBootBlog.repository.UserRepository;

@Service("userService")
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
}

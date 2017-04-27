package springBootBlog.service;

import springBootBlog.entity.User;

import java.util.List;

public interface UserService {
    User getCurrentUser();
    User createUser(String email, String fullName, String password);
    void saveAndFlush(User user);
    List<User> findAll();
}

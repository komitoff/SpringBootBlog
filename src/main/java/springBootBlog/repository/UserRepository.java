package springBootBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import springBootBlog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
package springBootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springBootBlog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
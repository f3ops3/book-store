package springweb.courseproject.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import springweb.courseproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}

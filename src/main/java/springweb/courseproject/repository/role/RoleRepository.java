package springweb.courseproject.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import springweb.courseproject.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(Role.RoleName roll);
}

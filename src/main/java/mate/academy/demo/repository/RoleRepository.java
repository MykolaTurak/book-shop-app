package mate.academy.demo.repository;

import java.util.Optional;
import mate.academy.demo.model.Role;
import mate.academy.demo.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByRoleName(@Param("name") RoleName roleName);
}

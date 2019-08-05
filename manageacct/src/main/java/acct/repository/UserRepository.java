package acct.repository;

import acct.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long > {

    User findOneByNickNameAndArea(String name,Long area);
    User findOneByAccountIdAndArea(Long accountId,Long area);
}

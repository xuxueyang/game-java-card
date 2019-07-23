package acct.repository;

import acct.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findOneByAccesstoken(String accesstoken);

    List<Token> findAllByCreatedBy(Long userId);
}

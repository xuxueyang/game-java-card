package acct.repository;

import acct.domain.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraphRepository extends JpaRepository<Graph, Long > {

}

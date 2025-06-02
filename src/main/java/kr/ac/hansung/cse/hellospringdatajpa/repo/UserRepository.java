package kr.ac.hansung.cse.hellospringdatajpa.repo;

import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

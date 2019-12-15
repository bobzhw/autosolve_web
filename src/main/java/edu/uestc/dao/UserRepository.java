package edu.uestc.dao;
import edu.uestc.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zw on 2019/12/14.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
}

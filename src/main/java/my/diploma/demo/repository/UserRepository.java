package my.diploma.demo.repository;

import my.diploma.demo.objects.Bookkeeper;
import my.diploma.demo.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u where u.login = :login")
    User findByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false " +
            "END FROM User u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN false ELSE true " +
            "END FROM User u WHERE u.telegramToken = :telegramToken AND u.login = :login")
    boolean checkTelegramToken(@Param("telegramToken") String telegramToken,
                               @Param("login") String login);

    User findById(long id);
}

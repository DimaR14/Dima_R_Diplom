package my.diploma.demo.repository;

import my.diploma.demo.objects.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT u FROM Account u where u.login = :login")
    Account findByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false " +
            "END FROM Account u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    Account findById(long id);
}

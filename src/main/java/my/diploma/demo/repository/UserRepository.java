package my.diploma.demo.repository;

import my.diploma.demo.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByChatId(long id);

    User findById(long id);

    @Modifying
    @Query("DELETE FROM User b WHERE b.id = :id")
    void deleteById(@Param("id")long id);

    @Query("SELECT b FROM User b WHERE b.stateId = :state_Id AND b.account.id = :account_id")
    User findByStateIdAndAccount(@Param("state_Id") int state_Id,
                                 @Param("account_id") long account_id);

    @Query("SELECT b FROM User b WHERE b.account.id = :account_id")
    List<User> findByAccount(@Param("account_id") long account_id);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false " +
            "END FROM User b WHERE b.telegramToken = :telegram_token AND b.account.id = :account_id")
    boolean existsByTelegramTokenAndAccount(@Param("telegram_token") String telegram_token,
                                         @Param("account_id") long account_id);


    @Query("SELECT b FROM User b WHERE b.telegramToken = :telegram_token")
    User findByTelegramToken(@Param("telegram_token") String telegram_token);
}

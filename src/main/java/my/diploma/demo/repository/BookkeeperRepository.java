package my.diploma.demo.repository;

import my.diploma.demo.objects.Bookkeeper;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.objects.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookkeeperRepository extends JpaRepository<Bookkeeper,Long> {

    Bookkeeper findByChatId(long id);

    Bookkeeper findById(long id);

    @Modifying
    @Query("DELETE FROM Bookkeeper b WHERE b.id = :id")
    void deleteById(@Param("id")long id);

    @Query("SELECT b FROM Bookkeeper b WHERE b.stateId = :state_Id AND b.user.id = :user_id")
    Bookkeeper findByStateIdAndUser(@Param("state_Id") int state_Id,
                              @Param("user_id") long user_id);

    @Query("SELECT b FROM Bookkeeper b WHERE b.user.id = :user_id")
    List<Bookkeeper> findByUser(@Param("user_id") long user_id);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false " +
            "END FROM Bookkeeper b WHERE b.telegramToken = :telegram_token AND b.user.id = :user_id")
    boolean existsByTelegramTokenAndUser(@Param("telegram_token") String telegram_token,
                                         @Param("user_id") long user_id);


    @Query("SELECT b FROM Bookkeeper b WHERE b.telegramToken = :telegram_token")
    Bookkeeper findByTelegramToken(@Param("telegram_token") String telegram_token);
}

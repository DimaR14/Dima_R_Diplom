package my.diploma.demo.repository;

import my.diploma.demo.objects.Bookkeeper;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.objects.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookkeeperRepository extends JpaRepository<Bookkeeper,Long> {
    @Query("SELECT b FROM Bookkeeper b WHERE b.requisite = :requisite")
    Bookkeeper findByRequisite(@Param("requisite") String requisite);

    Bookkeeper findByChatId(long id);

    @Query("SELECT b FROM Bookkeeper b WHERE b.stateId = :state_Id AND b.user.id = :user_id")
    Bookkeeper findByStateIdAndUser(@Param("state_Id") int state_Id,
                              @Param("user_id") long user_id);

    @Query("SELECT b FROM Bookkeeper b WHERE b.user.id = :user_id")
    List<Bookkeeper> findByUser(@Param("user_id") long user_id);

}

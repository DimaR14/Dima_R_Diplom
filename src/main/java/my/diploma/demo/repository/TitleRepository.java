package my.diploma.demo.repository;

import my.diploma.demo.objects.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TitleRepository  extends JpaRepository<Title, Long> {
    @Query("SELECT t FROM Title t where t.name = :name")
    Title findByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false " +
            "END FROM Title t WHERE t.name = :name AND t.user.id = :user_id")
    boolean existsByNameAndUser(@Param("name") String name,
                                @Param("user_id") long user_id);

    @Query("SELECT t FROM Title t WHERE t.user.id = :user_id")
    List<Title> getAllByUser(@Param("user_id") long user_id);
}

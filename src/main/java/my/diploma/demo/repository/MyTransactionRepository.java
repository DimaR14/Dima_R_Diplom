package my.diploma.demo.repository;

import my.diploma.demo.objects.MyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyTransactionRepository extends JpaRepository<MyTransaction,Long> {
    @Query("SELECT m FROM MyTransaction m WHERE m.user.account.id = :account_id")
    List<MyTransaction> getAllTransaction(@Param("account_id")long account_id);

    @Query("SELECT m FROM MyTransaction m WHERE m.id = :id")
    MyTransaction findById(@Param("id")long id);

    @Query("SELECT m FROM MyTransaction m WHERE m.user.id = :user_id")
    List<MyTransaction> getAllTransactionByUser(@Param("user_id")long user_id);

    @Query("SELECT m FROM MyTransaction m WHERE m.title = :title AND m.user.account.id = :account_id")
    List<MyTransaction> getAllByTitleAndAccount(@Param("title")String title,
                                             @Param("account_id")long account_id);

}




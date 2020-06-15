package my.diploma.demo.service;

import my.diploma.demo.objects.Bookkeeper;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.objects.User;
import my.diploma.demo.repository.BookkeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookkeeperService {

    @Autowired
    private BookkeeperRepository bookkeeperRepository;

    @Transactional(readOnly = true)
    public List<Bookkeeper> getAllBookkeeper() {
        return bookkeeperRepository.findAll();
    }

    @Transactional
    public Bookkeeper findByChatId(long id) {
        return bookkeeperRepository.findByChatId(id);
    }

    @Transactional(readOnly = true)
    public Bookkeeper findByRequisite(String requisite) {
        return bookkeeperRepository.findByRequisite(requisite);
    }


    @Transactional
    public void addBookkeeper(Bookkeeper bookkeeper) {
        bookkeeperRepository.save(bookkeeper);
    }


    @Transactional
    public void deleteBookkeeper(String requisite) {
        Bookkeeper bookkeeper = findByRequisite(requisite);
        bookkeeperRepository.deleteById(bookkeeper.getId());
    }

    @Transactional
    public void addMyTransaction(MyTransaction transaction, Bookkeeper bookkeeper) {
        bookkeeper.addTransaction(transaction);
     //   if (transaction.getAttribute().equals("+")) {
      //      bookkeeper.setBalance(bookkeeper.getBalance() + transaction.getSum());
      //  } else
        //    bookkeeper.setBalance(bookkeeper.getBalance() - transaction.getSum());
        bookkeeperRepository.save(bookkeeper);
    }

    @Transactional
    public void deleteMyTransaction(MyTransaction transaction, Bookkeeper bookkeeper) {
        List<MyTransaction> list = bookkeeper.getTransactions();
        list.remove(transaction);
        bookkeeper.setTransactions(list);
        bookkeeperRepository.save(bookkeeper);
    }


    @Transactional
    public void updateBookkeeper(Bookkeeper bookkeeper) {
        bookkeeperRepository.save(bookkeeper);
    }

    @Transactional
    public Bookkeeper findByStateIdAndUser(int stateId, long user_id) {
        return bookkeeperRepository.findByStateIdAndUser(stateId, user_id);
    }

    @Transactional
    public List<Bookkeeper> findByUser(User user) {
        return bookkeeperRepository.findByUser(user.getId());
    }


}



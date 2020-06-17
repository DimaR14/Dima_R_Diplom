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
    public Bookkeeper findById(long id){ return bookkeeperRepository.findById(id);}

    @Transactional
    public Bookkeeper findByChatId(long id) {
        return bookkeeperRepository.findByChatId(id);
    }


    @Transactional
    public void addBookkeeper(Bookkeeper bookkeeper) {
        bookkeeperRepository.save(bookkeeper);
    }


    @Transactional
    public void deleteBookkeeperById(long id) {
        bookkeeperRepository.deleteById(id);
    }

    @Transactional
    public void addMyTransaction(MyTransaction transaction, Bookkeeper bookkeeper) {
        bookkeeper.addTransaction(transaction);
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

    @Transactional
    public boolean existsByTelegramTokenAndUser(String telegramToken,User user){
        if(bookkeeperRepository.existsByTelegramTokenAndUser(telegramToken,user.getId())==false)
            return false;
        else return true;
    }

    @Transactional
    public Bookkeeper findByTelegramToken(String telegramToken){return bookkeeperRepository.findByTelegramToken(telegramToken);}

}



package my.diploma.demo.service;

import my.diploma.demo.objects.Account;
import my.diploma.demo.objects.User;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(long id){ return userRepository.findById(id);}

    @Transactional
    public User findByChatId(long id) {
        return userRepository.findByChatId(id);
    }


    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }


    @Transactional
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void addMyTransaction(MyTransaction transaction, User user) {
        user.addTransaction(transaction);
        userRepository.save(user);
    }

    @Transactional
    public void deleteMyTransaction(MyTransaction transaction, User user) {
        List<MyTransaction> list = user.getTransactions();
        list.remove(transaction);
        user.setTransactions(list);
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User findByStateIdAndAccount(int stateId, long account_id) {
        return userRepository.findByStateIdAndAccount(stateId, account_id);
    }

    @Transactional
    public List<User> findByAccount(Account account) {
        return userRepository.findByAccount(account.getId());
    }

    @Transactional
    public boolean existsByTelegramTokenAndAccount(String telegramToken, Account account){
        if(userRepository.existsByTelegramTokenAndAccount(telegramToken, account.getId())==false)
            return false;
        else return true;
    }

    @Transactional
    public User findByTelegramToken(String telegramToken){return userRepository.findByTelegramToken(telegramToken);}

}



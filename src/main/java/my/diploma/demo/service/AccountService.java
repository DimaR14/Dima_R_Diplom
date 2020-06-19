package my.diploma.demo.service;

import my.diploma.demo.objects.Account;
import my.diploma.demo.objects.User;
import my.diploma.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Account> getAllAccount(){return accountRepository.findAll();}

    @Transactional(readOnly = true)
    public Account findByLogin (String login){return  accountRepository.findByLogin(login);}

    @Transactional(readOnly = true)
    public Account findById (long id){return  accountRepository.findById(id);}

    @Transactional
    public boolean addAccount(String login, String password){
        if(accountRepository.existsByLogin(login))
            return false;
        Account account = new Account(login,passwordEncoder.encode(password));
        accountRepository.save(account);

        return true;
    }

    @Transactional
    public void deleteAccount(String login){
        Account account = findByLogin(login);
        accountRepository.deleteById(account.getId());
    }

    @Transactional
    public boolean existsByLogin(String login){
        if(accountRepository.existsByLogin(login)==false)
            return false;
        else return true;
    }

    @Transactional
    public void refresh(String login){
        Account account = accountRepository.findByLogin(login);
        double balance = 0;
        for(User user : account.getUsers()){
             balance = balance + user.getBalance();
        }
        account.setBalance(balance);
        accountRepository.save(account);
    }

}

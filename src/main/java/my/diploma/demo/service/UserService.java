package my.diploma.demo.service;

import my.diploma.demo.objects.Bookkeeper;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.objects.User;
import my.diploma.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){return userRepository.findAll();}

    @Transactional(readOnly = true)
    public User findByLogin (String login){return  userRepository.findByLogin(login);}

    @Transactional(readOnly = true)
    public User findById (long id){return  userRepository.findById(id);}

    @Transactional
    public boolean addUser(String login, String password){
        if(userRepository.existsByLogin(login))
            return false;
        User user = new User(login,passwordEncoder.encode(password));
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void deleteUser(String login){
        User user = findByLogin(login);
        userRepository.deleteById(user.getId());
    }

    @Transactional
    public boolean existsByLogin(String login){
        if(userRepository.existsByLogin(login)==false)
            return false;
        else return true;
    }

    @Transactional
    public void refresh(String login){
        User user = userRepository.findByLogin(login);
        double balance = 0;
        for(Bookkeeper bookkeeper : user.getBookkeepers()){
             balance = balance + bookkeeper.getBalance();
        }
        user.setBalance(balance);
        userRepository.save(user);
    }

}
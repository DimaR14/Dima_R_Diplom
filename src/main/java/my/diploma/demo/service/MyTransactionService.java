package my.diploma.demo.service;

import my.diploma.demo.objects.Bookkeeper;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.objects.User;
import my.diploma.demo.repository.MyTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MyTransactionService {

    @Autowired
    private MyTransactionRepository myTransactionRepository;

    @Transactional(readOnly = true)
    public List<MyTransaction> getAllMyTransaction(){return myTransactionRepository.findAll();}

    @Transactional
    public List<MyTransaction> getAllTransactionFromUser(User user){
        return  myTransactionRepository.getAllTransaction(user.getId());
    }

    @Transactional
    public long count(){return myTransactionRepository.count();}

    @Transactional
    public void deleteTransaction(long id){
        myTransactionRepository.deleteById(id);
    }

    @Transactional
    public void deleteTransactions(long[] id_list){
        for(long id : id_list)
            myTransactionRepository.deleteById(id);
    }

    @Transactional
    public MyTransaction findById(long id){return myTransactionRepository.findById(id);}

    @Transactional
    public List<MyTransaction> getAllTransactionByBookkeeper(Bookkeeper bookkeeper){
        return myTransactionRepository.getAllTransactionByBookkeeper(bookkeeper.getId());
    }

    @Transactional
    public List<MyTransaction> getAllTransactionByTitleAndUser(String title, User user){
        return myTransactionRepository.getAllByTitleAndUser(title,user.getId());
    }

}

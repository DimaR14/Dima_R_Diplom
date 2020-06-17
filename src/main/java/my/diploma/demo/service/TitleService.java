package my.diploma.demo.service;

import my.diploma.demo.objects.Title;
import my.diploma.demo.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TitleService {
    @Autowired
    private TitleRepository titleRepository;

    @Transactional(readOnly = true)
    public List<Title> getAllTitle(){return titleRepository.findAll();}

    @Transactional(readOnly = true)
    public Title findByName (String name){return titleRepository.findByName(name);}


    @Transactional
    public boolean existsByNameAndUser(String name,long user_id){
        if(titleRepository.existsByNameAndUser(name,user_id)==false)
           return false;
        else return true;
    }

    @Transactional
    public void addTitle(Title title){
        titleRepository.save(title);
    }

    @Transactional
    public List<Title> getAllByUser(long user_id){return titleRepository.getAllByUser(user_id);}

}

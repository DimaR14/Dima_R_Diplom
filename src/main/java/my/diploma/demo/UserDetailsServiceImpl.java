package my.diploma.demo;

import my.diploma.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        my.diploma.demo.objects.User user = userService.findByLogin(login);
        if (user == null)
            throw new UsernameNotFoundException(login + " not found");
        List<GrantedAuthority> roles =
                Arrays.asList(new SimpleGrantedAuthority("=="));
        return new User(user.getLogin(),user.getPassword(),roles);
    }

}
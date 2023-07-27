package sg.nus.iss.facialrecognition.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import sg.nus.iss.facialrecognition.model.Role;
import sg.nus.iss.facialrecognition.repository.UserRepository;
 
@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        sg.nus.iss.facialrecognition.model.User retrievedUser = userRepository.findById(username).get();

        if (retrievedUser == null || !retrievedUser.getToken().equals("1") || retrievedUser.isActive() == false) {
            throw new UsernameNotFoundException("User not found");
        } else {
            Role role = retrievedUser.getRole();
            Set<GrantedAuthority> ga = new HashSet<>();
			ga.add(new SimpleGrantedAuthority(role.getRoleName()));

           return new org.springframework.security.core.userdetails.User(
							username,
							encoder.encode(retrievedUser.getPassword()), ga);
            
        }
    }

    




 
}

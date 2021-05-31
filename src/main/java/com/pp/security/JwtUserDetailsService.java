package com.pp.security;


import com.pp.user.UserJpa;
import com.pp.user.UserModel;
import com.pp.user.UserRepository;
import com.pp.user.role.Role;
import com.pp.user.role.RoleJpa;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;


    public JwtUserDetailsService(UserRepository userRepository,
                                 PasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User not found with username: %s", username));
        }

        return new User(user.getEmail(),user.getPassword(),getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserJpa user) {
        return user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(String.format("ROLE_%s", r.getName())))
                .collect(Collectors.toSet());
    }

    public UserJpa save(UserModel userModel){
        var newUser = UserJpa.builder()
                .email(userModel.getEmail())
                .roles(roleJpas.apply(userModel.getRoles()))
                .password(this.bcryptEncoder.encode(userModel.getPassword()))
                .fullName(userModel.getFullName())
                .phone(userModel.getPhone())
                .address(userModel.getAddress())
                .build();

        return userRepository.save(newUser);
    }

    private final Function<Set<Role>, Set<RoleJpa>> roleJpas = (roles) ->  roles.stream()
            .map(r -> RoleJpa.builder().name(r.name()).build())
            .collect(Collectors.toSet());
}

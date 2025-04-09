package io.github.pdv.sistema.pdv.security;

import io.github.pdv.sistema.pdv.dto.LoginDto;
import io.github.pdv.sistema.pdv.entity.User;
import io.github.pdv.sistema.pdv.exeception.PasswordNotFoundException;
import io.github.pdv.sistema.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);

        if (user == null){
            throw new UsernameNotFoundException("Login inválido!");
        }

        return new UserPrincipal(user);
    }

    public void verifyUserCredencials(LoginDto login){
        UserDetails user = loadUserByUsername(login.getUsername());
        boolean passwordIsTheSame = SecurityConfig.passwordEncoder()
                .matches(login.getPassword(), user.getPassword());

        if (!passwordIsTheSame){
            throw new PasswordNotFoundException("Senha invalida");
        }
    }




}

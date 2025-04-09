package io.github.pdv.sistema.pdv.service;

import io.github.pdv.sistema.pdv.dto.UserDto;
import io.github.pdv.sistema.pdv.dto.UserResponseDto;
import io.github.pdv.sistema.pdv.entity.User;
import io.github.pdv.sistema.pdv.exeception.NoItemException;
import io.github.pdv.sistema.pdv.repository.UserRepository;
import io.github.pdv.sistema.pdv.security.SecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper mapper = new ModelMapper();


    public List<UserResponseDto> findAll(){
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.isEnabled()
                )).collect(Collectors.toList());
    }

    public UserDto save(UserDto user){
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = mapper.map(user, User.class);
        userRepository.save(userToSave);
        return new UserDto(
                userToSave.getId(),
                userToSave.getName(),
                userToSave.getUsername(),
                userToSave.getPassword(),
                userToSave.isEnabled()
        );

    }

    public UserDto findById(long id){
        Optional<User> optional =  userRepository.findById(id);
        if (!optional.isPresent()){
            throw new NoItemException("Usuario não encontrado");
        }
        User user = optional.get();
        return new UserDto(user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled());
    }

    public UserDto update(UserDto user){
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = mapper.map(user, User.class);
        Optional<User> userToEdit = userRepository.findById(userToSave.getId());

        if (!userToEdit.isPresent()){
            throw new NoItemException("Usuario não encontrado");
        }
        userRepository.save(userToSave);

        return new UserDto(user.getId(),
                userToSave.getName(),
                userToSave.getUsername(),
                userToSave.getPassword(),
                userToSave.isEnabled());
    }

    public void deleteById(long id){
        if (!userRepository.existsById(id)){
            throw new EmptyResultDataAccessException(1);
        }
        userRepository.deleteById(id);
    }

    public User getByUserName(String username){
        return userRepository.findUserByUsername(username);
    }




}

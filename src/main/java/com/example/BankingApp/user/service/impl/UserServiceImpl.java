package com.example.BankingApp.user.service.impl;

import com.example.BankingApp.exception.NoResultFoundException;
import com.example.BankingApp.exception.WrongRoleException;
import com.example.BankingApp.user.converter.UserConverter;
import com.example.BankingApp.user.dto.UserDto;
import com.example.BankingApp.user.dto.UserRequestDto;
import com.example.BankingApp.user.enums.RoleEnum;
import com.example.BankingApp.user.model.Role;
import com.example.BankingApp.user.model.User;
import com.example.BankingApp.user.repository.RoleRepository;
import com.example.BankingApp.user.repository.UserRepository;
import com.example.BankingApp.user.service.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
    }

    @Override
    public void addBanker(UserRequestDto userRequestDto) {
            User user = addUser(userRequestDto,RoleEnum.ROLE_BANKER);
            userRepository.save(user);
    }

    @Override
    public void addClient(UserRequestDto userRequestDto) {
        User user = addUser(userRequestDto,RoleEnum.ROLE_CLIENT);
        userRepository.save(user);
    }

    @Override
    public void updateBanker(String email, UserDto dto) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new NoResultException("user is not found"));
        if (!hasRoleBanker(user)){
            throw new WrongRoleException("Admin could update only bankers");
        }
        user = userConverter.convertToUser(user,dto);
        userRepository.save(user);
    }

    @Override
    public void updateClient(String email, UserDto dto) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new NoResultFoundException("user is not found"));
        if (!hasRoleClient(user)){
            throw new WrongRoleException("Banker could update only clients");
        }
        user = userConverter.convertToUser(user,dto);
        userRepository.save(user);
    }

    @Override
    public void deleteClient(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new NoResultFoundException("user is not found"));
        if (!hasRoleClient(user)){
            throw new WrongRoleException("Banker could delete only clients");
        }
        userRepository.delete(user);
    }

    @Override
    public void deleteBanker(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new NoResultFoundException("user is not found"));
        if(!hasRoleBanker(user)){
            throw new WrongRoleException("Admin could delete only bankers");
        }
        userRepository.delete(user);
    }

    private User addUser(UserRequestDto userRequestDto, RoleEnum roleEnum){
        User user = userConverter.convertToUser(userRequestDto);
        Role role = roleRepository.findByRoleEnum(roleEnum).orElseThrow(()->new NoResultFoundException("role is not found"));
        user.setRoles(Collections.singleton(role));
        return user;
    }
    public boolean hasRoleBanker(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRoleEnum().getValue().equals(RoleEnum.ROLE_BANKER.getValue()));
    }
    public boolean hasRoleClient(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRoleEnum().getValue().equals(RoleEnum.ROLE_CLIENT.getValue()));
    }
}

package com.example.BankingApp.user.service.impl;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Integer addBanker(UserRequestDto userRequestDto) {
            User user = addUser(userRequestDto,RoleEnum.ROLE_BANKER);
            userRepository.save(user);
            return user.getId();

    }

    @Override
    public Integer addClient(UserRequestDto userRequestDto) {
        User user = addUser(userRequestDto,RoleEnum.ROLE_CLIENT);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Integer updateBanker(Integer id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(()->new NoResultException("user is not found"));
        if (hasRoleBanker(user)) {
            if (dto.getEmail() != null) {
                user.setEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            if (dto.getRole() != null) {
                Role role = roleRepository.findByRoleEnum(RoleEnum.fromValue(dto.getRole()));
                user.getRoles().add(role);

            }
            userRepository.save(user);
            return user.getId();
        }
        return null;

    }

    @Override
    public Integer updateClient(Integer id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(()->new NoResultException("user is not found"));
        if (hasRoleClient(user)) {
            if (dto.getEmail() != null) {
                user.setEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            if (dto.getRole() != null) {
                Role role = roleRepository.findByRoleEnum(RoleEnum.fromValue(dto.getRole()));
                user.getRoles().add(role);

            }
            userRepository.save(user);
            return user.getId();
        }
        return null;

    }

    @Override
    public void deleteClient(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()->new NoResultException("user is not found"));
        if(hasRoleClient(user)){
            userRepository.delete(user);
        }
    }



    @Override
    public void deleteBanker(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()->new NoResultException("user is not found"));
        if(hasRoleBanker(user)){
            userRepository.delete(user);
        }
    }

    private User addUser(UserRequestDto userRequestDto, RoleEnum roleEnum){
        User user = userConverter.convertToUser(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Role role = roleRepository.findByRoleEnum(roleEnum);
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

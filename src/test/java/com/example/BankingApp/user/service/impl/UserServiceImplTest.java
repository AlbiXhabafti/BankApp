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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private  UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userService;
    @Test
    void addBanker() {
        UserRequestDto dto = createUserRequestDto("albi", "xhabafti", "albi@gmail.com", "password", RoleEnum.ROLE_BANKER);
        Role role = createRole(1, RoleEnum.ROLE_BANKER);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);

        Mockito.when(userConverter.convertToUser(dto)).thenReturn(user);
        Mockito.when(roleRepository.findByRoleEnum(RoleEnum.ROLE_BANKER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.addBanker(dto);


        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getPassword(), user.getPassword());
        assertEquals(dto.getLastName(), user.getLastName());
        verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void addClient() {
        UserRequestDto dto = createUserRequestDto("albi", "xhabafti", "albi@gmail.com", "password", RoleEnum.ROLE_CLIENT);
        Role role = createRole(1, RoleEnum.ROLE_CLIENT);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);

        Mockito.when(userConverter.convertToUser(dto)).thenReturn(user);
        Mockito.when(roleRepository.findByRoleEnum(RoleEnum.ROLE_CLIENT)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.addClient(dto);


        verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void updateBanker() {
        String email = "albi@gmail.com";
        UserDto dto = createUserDto("test", "testLastName", "test@gmail.com", "1234");
        Role role = createRole(1, RoleEnum.ROLE_BANKER);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);

        when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));
        when(userConverter.convertToUser(user, dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.updateBanker(email, dto);


        verify(userRepository, Mockito.times(1)).save(user);
    }
    @Test
    void updateBanker_throwsException_when_user_found_fromEmail_isNot_Banker(){
        String email = "albi@gmail.com";
        UserDto dto = createUserDto("test", "testLastName", "test@gmail.com", "1234");
        Role role = createRole(1, RoleEnum.ROLE_CLIENT);

        User user = createUser(1, "albi", "xhabafti", email, "password", role);
        when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));

        assertThrows(WrongRoleException.class, () -> userService.updateBanker(email,dto));

    }
    @Test
    void updateClient() {
        String email = "albi@gmail.com";
        UserDto dto = createUserDto("test", "testLastName", "test@gmail.com", "1234");
        Role role = createRole(1, RoleEnum.ROLE_CLIENT);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);

        when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));
        when(userConverter.convertToUser(user, dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.updateClient(email, dto);


        verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    void updateClient_throwsException_when_user_found_fromEmail_isNot_Client(){

        String email = "albi@gmail.com";
        UserDto dto = createUserDto("test", "testLastName", "test@gmail.com", "1234");
        Role role = createRole(1, RoleEnum.ROLE_BANKER);

        User user = createUser(1, "albi", "xhabafti", email, "password", role);
        when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));

        assertThrows(WrongRoleException.class, () -> userService.updateClient(email,dto));

    }
    @Test
    void updateClient_throwsException_when_fromEmail_isNot_found_user(){
        String email = "albi@gmail.com";
        UserDto dto = createUserDto("test", "testLastName", "test@gmail.com", "1234");
        Role role = createRole(1, RoleEnum.ROLE_BANKER);

        User user = createUser(1, "albi", "xhabafti", email, "password", role);

        assertThrows(NoResultFoundException.class, () -> userService.updateClient(email,dto));

    }
    @Test
    void deleteClient() {
        String email = "albi@gmail.com";
        Role role = createRole(1, RoleEnum.ROLE_CLIENT);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);
        when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteClient(email);
        verify(userRepository, Mockito.times(1)).delete(user);
    }
    @Test
    void deleteBanker() {
        String email = "albi@gmail.com";
        Role role = createRole(1, RoleEnum.ROLE_BANKER);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);
        when(userRepository.findByEmailAndDeletedFalse(email)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteBanker(email);
        verify(userRepository, Mockito.times(1)).delete(user);
    }


    @Test
    void deleteBanker_throw_exception() {
        String email = "albi@gmail.com";
        Role role = createRole(1, RoleEnum.ROLE_CLIENT);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);

        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(java.util.Optional.of(user));
        assertThrows(WrongRoleException.class, () -> userService.deleteBanker("banker@gmail.com"));

    }
    @Test
    void deleteClient_throw_exception() {
        String email = "albi@gmail.com";
        Role role = createRole(1, RoleEnum.ROLE_BANKER);
        User user = createUser(1, "albi", "xhabafti", "albi@gmail.com", "password", role);

        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(java.util.Optional.of(user));
        assertThrows(WrongRoleException.class, () -> userService.deleteClient("client@gmail.com"));

    }
    private UserRequestDto createUserRequestDto(String firstName, String lastName, String email, String password, RoleEnum roleEnum) {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    private UserDto createUserDto(String firstName, String lastName, String email, String password) {
        UserDto dto = new UserDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    private Role createRole(int id, RoleEnum roleEnum) {
        Role role = new Role();
        role.setId(id);
        role.setRoleEnum(roleEnum);
        return role;
    }

    private User createUser(int id, String firstName, String lastName, String email, String password, Role role) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setDeleted(false);
        user.setRoles(Collections.singleton(role));
        return user;
    }


}
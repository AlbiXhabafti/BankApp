package com.example.BankingApp.user.contoller;


import com.example.BankingApp.user.dto.UserRequestDto;
import com.example.BankingApp.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;


  //  @Test
    void addBanker_withAdminRole() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("albi");
        dto.setLastName("xhabafti");
        dto.setEmail("albi@gmail.com");
        dto.setPassword("12234");
        dto.setEmail(dto.getEmail());

        Principal loggedUser = new Principal() {
            @Override
            public String getName() {
                return "albi@gmail.com";
            }

        };
        Authentication authentication = new UsernamePasswordAuthenticationToken("albi@gmail.com", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Mockito.doNothing().when(userService).addBanker(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/banker")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .principal(loggedUser))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
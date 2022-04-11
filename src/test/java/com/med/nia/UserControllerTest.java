package com.med.nia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.med.nia.api.UserController;
import com.med.nia.dto.UserDto;
import com.med.nia.dto.UserInformationDto;
import com.med.nia.service.UserService;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllUsersTest() throws Exception {
        List<UserDto> userDtos = List.of(UserDto.builder().userName("Gary").name("Becker").id(1).build(),
                UserDto.builder().userName("John").name("Doe").id(2).build());

        when(userService.getAllUsers()).thenReturn(userDtos);
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].userName", Matchers.is("Gary")))
                .andExpect(jsonPath("$[0].name", Matchers.is("Becker")))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].userName", Matchers.is("John")))
                .andExpect(jsonPath("$[1].name", Matchers.is("Doe")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));

    }

    @SneakyThrows
    @Test
    public void saveUserTest() {
        UserDto user = UserDto.builder().userName("John").name("Doe").age(22).build();
        UserInformationDto useInfoDto = UserInformationDto.builder().userName("John").name("Doe").age(22).build();
        when(userService.addUser(any(UserInformationDto.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                .content(mapper.writeValueAsString(useInfoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("John"))
                .andExpect(jsonPath("$.name", Matchers.is("Doe")))
                .andExpect(jsonPath("$.age", Matchers.is(22)));

    }

    @SneakyThrows
    @Test
    public void deleteUserTest() {
        int userIdToDelete = 1;
        when(userService.deleteUserById(anyInt())).thenReturn(userIdToDelete);

        mockMvc.perform(delete("/user")
                .param("userId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }
}

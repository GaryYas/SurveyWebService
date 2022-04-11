package com.med.nia;

import com.med.nia.dto.UserDto;
import com.med.nia.dto.UserInformationDto;
import com.med.nia.exception.ResourceNotFoundException;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.UserRepository;
import com.med.nia.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Test
    public void addUserTest() {
        UserInformationDto userInformationDto = UserInformationDto.builder().userName("Johnny")
                .age(20)
                .name("Warren")
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity("Warren", "Johnny", 20));
        UserDto userDto = userService.addUser(userInformationDto);
        assertTrue(userDto.getAge() == userInformationDto.getAge() && userDto.getUserName().equals(userInformationDto.getUserName())
                && userDto.getName().equals(userInformationDto.getName()));

    }

    @Test
    public void deleteUser() {
        int userId = 1;
        when(userRepository.existsById(eq(userId))).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(userId);
        int deletedId = userService.deleteUserById(userId);
        assertEquals(userId, deletedId);
    }

    @Test()
    public void deleteUserWithNotExistedId() {
        int userId = 1;
        when(userRepository.existsById(eq(userId))).thenReturn(false);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.deleteUserById(userId));
        String message = exception.getMessage();
        assertTrue(message.contains("user with id " + userId + " does not exist"));

    }

    @Test
    public void findAllUsers() {
        List<UserEntity> userList = List.of(new UserEntity("ono", "tip", 34),
                new UserEntity("Deni", "Avdjia", 34));
        when(userRepository.findAll()).thenReturn(userList);
        List<UserDto> fetchedUsers = userService.getAllUsers();
        assertEquals(2, fetchedUsers.size());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void findUserById() {
        int userId = 1;
        UserEntity userEntity = new UserEntity("Deni", "Avdjia", 34);
        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(userEntity));
        UserDto fetchedUser = userService.getUserByID(userId);
        assertEquals(fetchedUser.getUserName(), userEntity.getUserName());
        assertEquals(fetchedUser.getName(), userEntity.getName());
        assertEquals(fetchedUser.getAge(), userEntity.getAge());
    }
}

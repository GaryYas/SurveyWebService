package com.med.nia.service;

import com.med.nia.dto.UserDto;
import com.med.nia.dto.UserInformationDto;
import com.med.nia.exception.ResourceNotFoundException;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.med.nia.service.ServiceUtils.transformFromUserEntityToDto;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * persist user for further survey completion
     * @param userDto information of the user
     * @return new added user dto
     */
    public UserDto addUser(UserInformationDto userDto) {
        UserEntity user = userRepository.save(new UserEntity(userDto.getName(), userDto.getUserName(), userDto.getAge()));
        return transformFromUserEntityToDto(user);

    }

    /**
     * Retrieves all the users
     */
    public List<UserDto> getAllUsers() {
        return transformFromUserEntityToDto(userRepository.findAll());
    }

    /**
     * Retrieves the user by id from data base
     *
     * @param id of the user to be retrieved
     * @return retrieved user
     * throws ResourceNotFoundException in case no user with such id exist
     */
    public UserDto getUserByID(int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("user with id " + id + " does not exist"));

        return transformFromUserEntityToDto(userEntity);
    }

    /**
     * Deletes user by his id
     *
     * @param id of the user to be deleted
     * @return id of the deleted user
     * throws ResourceNotFoundException in case no user with such id exist
     */
    public int deleteUserById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return id;
        }

        throw new ResourceNotFoundException("user with id " + id + " does not exist");
    }

    /**
     * Deletes all the users and their surveys
     *
     * @return
     */
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
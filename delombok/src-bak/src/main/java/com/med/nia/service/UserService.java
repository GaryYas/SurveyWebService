package com.med.nia.service;

import com.med.nia.dto.UserDto;
import com.med.nia.dto.UserInformationDto;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.med.nia.service.ServiceUtils.transformFromUserEntityToDto;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * persist user for further survey completion
     *
     * @param userDto information of the user
     * @return persisted user (200)
     * (500) for internal server error
     */
    public ResponseEntity<UserDto> addUser(UserInformationDto userDto) {

        try {
            UserEntity user = userRepository.save(new UserEntity(userDto.getName(), userDto.getUserName(), userDto.getAge()));
            return new ResponseEntity<>(transformFromUserEntityToDto(user), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Retrieves all the users
     *
     * @return (200) list of all users
     * (500) for internal server error
     */
    public ResponseEntity<List<UserDto>> getAllUsers() {

        try {
            return new ResponseEntity<>(transformFromUserEntityToDto(userRepository.findAll()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves the use by id from data base
     *
     * @param id of the user to be retrieved
     * @return user (200)
     * (400) if no such user exist
     * (500) server internal error
     */
    public ResponseEntity<UserDto> getUserByID(int id) {
        try {
            Optional<UserEntity> userEntity = userRepository.findById(id);

            if (userEntity.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(transformFromUserEntityToDto(userEntity.get()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *  Deletes user by his id
     * @param id of the user to be deleted
     * @return id of the deleted user(200)
     * (400) for internal server error
     */
    public ResponseEntity<Integer> deleteUserById(int id) {
        try {
            if (userRepository.existsById(id))
                userRepository.deleteById(id);

            return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes all the users and their surveys
     * @return
     */
    public ResponseEntity<Void> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
package com.med.nia.api;

import com.med.nia.dto.SurveyDto;
import com.med.nia.dto.UserDto;
import com.med.nia.dto.UserInformationDto;
import com.med.nia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User for survey", description = "The user API to be performed on survey")
/**
 * Controller for adding , retirieving and deleting  user
 * created By :Gary
 */
public class UserController {

    @Autowired
    UserService userService;


    @Operation(summary = "Saves new user", tags = {"User for survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInformationDto.class))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)})

    @PostMapping(value = "/user", produces = "application/json")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserInformationDto userDto) {
        UserDto addedUser = userService.addUser(userDto);
        return ResponseEntity.ok(addedUser);
    }

    @Operation(summary = "Get User by user id with all related surveys", tags = {"User for survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retrieved user by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user for not found",
                    content = @Content)})
    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<UserDto> getUserById(@RequestParam String userId) {

        UserDto fetchedUser = userService.getUserByID(Integer.parseInt(userId));
        return ResponseEntity.ok(fetchedUser);

    }

    @Operation(summary = "retrieves all existed user , with their surveys", tags = {"User for survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retrieved all users",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SurveyDto.class)))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)})

    @GetMapping(value = "/user/all", produces = "application/json")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> fetchedUsers = userService.getAllUsers();
        return ResponseEntity.ok(fetchedUsers);
    }

    @Operation(summary = "Delete user by user id", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "delete survey and return id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)})
    @DeleteMapping(value = "/user", produces = "application/json")
    public ResponseEntity<Integer> deleteUsersById(@RequestParam String userId) {
        return ResponseEntity.ok(userService.deleteUserById(Integer.parseInt(userId)));
    }

    @Operation(summary = "deletes all existed users with their surveys", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "delete all users ",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Integer.class)))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)
    })
    @DeleteMapping(value = "/user/all", produces = "application/json")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return  ResponseEntity.ok().build();
    }


}

package com.med.nia.api;


import com.med.nia.dto.SurveyDto;
import com.med.nia.service.SurveyService;
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
@Tag(name = "survey", description = "The survey API")
/**
 * Controller for adding , retirieving and deleting new survey
 * created By :Gary
 */
public class SurveyController {

    @Autowired
    SurveyService surveyService;


    @Operation(summary = "Saves new survey", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new survey",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurveyDto.class))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user for not found",
                    content = @Content)})

    @PostMapping(value = "/survey", produces = "application/json")
    public ResponseEntity<SurveyDto> saveSurvey(@RequestBody SurveyDto surveyDto) {
        return surveyService.saveSurvey(surveyDto);
    }

    @Operation(summary = "retrieves the surveys which belongs to user", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new survey",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SurveyDto.class)))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user for not found",
                    content = @Content)})

    @GetMapping(value = "/survey/user", produces = "application/json")
    public ResponseEntity<List<SurveyDto>> getSurveysByUser(@RequestParam String userId) {
        return surveyService.getAllSurveysByUser(Integer.parseInt(userId));
    }

    @Operation(summary = "Get survey by survey id", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new survey",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurveyDto.class))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user for not found",
                    content = @Content)})

    @GetMapping(value = "/survey", produces = "application/json")
    public ResponseEntity<SurveyDto> getSurveysById(@RequestParam String surveyId) {
        return surveyService.getSurveyById(Integer.parseInt(surveyId));
    }

    @Operation(summary = "retrieves all existed surveys", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new survey",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SurveyDto.class)))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)})

    @GetMapping(value = "/survey/all", produces = "application/json")
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    @Operation(summary = "deletes all existed surveys", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "delete all surveys",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Integer.class)))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)
    })
    @DeleteMapping(value = "/survey/all", produces = "application/json")
    public ResponseEntity<Void> deleteAllSurveys() {
        return surveyService.deleteAllSurveys();
    }


    @Operation(summary = "deletes all surveys which belongs to user", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "delete survey by id succeeded",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Integer.class)))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user for not found",
                    content = @Content)})
    @DeleteMapping(value = "/survey/user", produces = "application/json")
    public ResponseEntity<List<Integer>> deleteSurveysByUser(@RequestParam String userId) {
        return surveyService.deleteAllSurveysBelongToUser(Integer.parseInt(userId));
    }


    @Operation(summary = "Delete survey by survey id", tags = {"survey"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new survey",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "user for not found",
                    content = @Content)})
    @DeleteMapping(value = "/survey", produces = "application/json")
    public ResponseEntity<Integer> deleteSurveysById(@RequestParam String surveyId) {
        return surveyService.deleteSurveyById(Integer.parseInt(surveyId));
    }

}

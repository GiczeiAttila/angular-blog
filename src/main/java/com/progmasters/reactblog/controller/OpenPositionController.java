package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.ApplicantForOpenPosition;
import com.progmasters.reactblog.domain.OpenPosition;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.ApplicationForOpenPositionDto;
import com.progmasters.reactblog.domain.dto.OpenPositionFormDto;
import com.progmasters.reactblog.domain.dto.OpenPositionListItemDto;
import com.progmasters.reactblog.service.ApplicantForOpenPositionService;
import com.progmasters.reactblog.service.OpenPositionService;
import com.progmasters.reactblog.service.UserService;
import com.progmasters.reactblog.validator.OpenPositionFormDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/open-positions")
public class OpenPositionController {
    private static final Logger logger = LoggerFactory.getLogger(OpenPositionController.class);
    private final OpenPositionService openPositionService;
    private final UserService userService;
    private final ApplicantForOpenPositionService applicantForOpenPositionService;
    private final OpenPositionFormDtoValidator openPositionFormDtoValidator;

    public OpenPositionController(OpenPositionService openPositionService, UserService userService, ApplicantForOpenPositionService applicantForOpenPositionService, OpenPositionFormDtoValidator openPositionFormDtoValidator) {
        this.openPositionService = openPositionService;
        this.userService = userService;
        this.applicantForOpenPositionService = applicantForOpenPositionService;
        this.openPositionFormDtoValidator = openPositionFormDtoValidator;
    }

    @InitBinder("openPositionFormDto")
    private void initOpenPositionFormDtoValidator(WebDataBinder binder) {
        binder.addValidators(openPositionFormDtoValidator);
    }

    @PostMapping
    public ResponseEntity<Void> createOpenPosition(@Valid @RequestBody OpenPositionFormDto openPositionFormDto) {
        logger.info("Create open position requested with user id:" + openPositionFormDto.getUserId() +
                            " with open position name: " + openPositionFormDto.getPositionName() +
                            " deadline: " + openPositionFormDto.getDeadline());
        OpenPosition openPosition = openPositionService.saveOpenPosition(openPositionFormDto);
        System.out.println(openPosition.getDeadline());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO Review - REST szerint ennek úgy kéne kinéznie, hogy az /api/open-positions/{id}/apply-ra
    // menne ez a kérés, ahol DTO-ban küldjük az módosításhoz szükséges adatokat
    // Ennek kb a lényege, hogy egy bizonyos resourcera küldünk valamilyen 'commandot'
    @PostMapping("/apply")
    public ResponseEntity<Void> applyForOpenPosition(@RequestBody ApplicationForOpenPositionDto applicationForOpenPositionDto) {
        logger.info("User with id:" + applicationForOpenPositionDto.getApplicantId() +
                            " applied for open position with id: " + applicationForOpenPositionDto.getOpenPositionId());
        OpenPosition openPosition = openPositionService.findOpenPositionById(applicationForOpenPositionDto.getOpenPositionId());
        User user = userService.findById(applicationForOpenPositionDto.getApplicantId());
        ApplicantForOpenPosition applicantForOpenPosition = new ApplicantForOpenPosition(user, openPosition);
        applicantForOpenPositionService.saveApplicant(applicantForOpenPosition);
        logger.info("Application requested by user with id:" + user.getId() +
                            " to open position with id: " + openPosition.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO Review - Ez szintén fordítva kéne hogy legyen, /api/open-positions/{id}/get-open-positions
    // Ezek lentebb/máshol szintén előfordulnak többször
    // Ebben most a szívás, hogy a frontendet is utána kell húzni...
    @GetMapping("/open-positions/{id}")
    public ResponseEntity<List<OpenPositionListItemDto>> getActiveOpenPositions(@PathVariable("id") Long id) {
        logger.info("Open position list requested");
        List<OpenPositionListItemDto> openPositionListItemDtos = openPositionService.getActiveOpenPositions(id);
        return new ResponseEntity<>(openPositionListItemDtos, HttpStatus.OK);
    }

    @GetMapping("/my-open-positions/{id}")
    public ResponseEntity<List<OpenPositionListItemDto>> getOpenPositionsWithUserId(@PathVariable("id") Long id) {
        logger.info("Open position list requested with user id: " + id);
        List<OpenPositionListItemDto> openPositionListItemDtos = openPositionService.getOpenPositionsByUserId(id);
        return new ResponseEntity<>(openPositionListItemDtos, HttpStatus.OK);
    }

    @GetMapping("/my-applications/{id}")
    public ResponseEntity<List<OpenPositionListItemDto>> getMyApplications(@PathVariable("id") Long id) {
        logger.info("Open position list requested with user id: " + id);
        List<OpenPositionListItemDto> openPositionListItemDtos = openPositionService.getOpenPositionsByUserId(id);
        return new ResponseEntity<>(openPositionListItemDtos, HttpStatus.OK);
    }
}

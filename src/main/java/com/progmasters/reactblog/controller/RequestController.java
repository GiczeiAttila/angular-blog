package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.TimeOffFormData;
import com.progmasters.reactblog.domain.dto.TimeOffListItem;
import com.progmasters.reactblog.domain.dto.TimeOffStatusChangeDto;
import com.progmasters.reactblog.domain.dto.UserTimeOffList;
import com.progmasters.reactblog.service.RequestService;
import com.progmasters.reactblog.validator.TimeOffValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private final RequestService requestService;
    private final TimeOffValidator timeOffValidator;

    @Autowired
    public RequestController(RequestService requestService, TimeOffValidator timeOffValidator) {
        this.requestService = requestService;
        this.timeOffValidator = timeOffValidator;
    }

    @InitBinder("timeOffFormData")
    private void initTimeOffValidator(WebDataBinder binder) {
        binder.addValidators(timeOffValidator);
    }

    @PostMapping("timeOffForm")
    public ResponseEntity saveTimeOff(@Valid @RequestBody TimeOffFormData timeOffFormData) {
        requestService.saveTimeOffDate(timeOffFormData);
        logger.info("Save new time off date range with user id: " + timeOffFormData.getUserId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("timeOff/{id}")
    public ResponseEntity<List<UserTimeOffList>> getTimeOffListById(@PathVariable Long id) {
        List<UserTimeOffList> timeOffList;
        User actualUser = this.requestService.findUserById(id);
        if (actualUser != null) {
            timeOffList = this.requestService.getUserTimeOffListById(id);
            logger.info("TimeOff list requested with user id: " + id);
            return new ResponseEntity<>(timeOffList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/timeOff/all")
    public ResponseEntity<List<TimeOffListItem>> getAllTimeOffList() {
        List<TimeOffListItem> list = this.requestService.getAllTimeOffList();
        logger.info("All timeOff list requested");
        return new ResponseEntity<List<TimeOffListItem>>(list, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> changeTimeOffStatus(@RequestBody TimeOffStatusChangeDto timeOffStatusChangeDto) {
        this.requestService.changeTimeOffStatus(timeOffStatusChangeDto);
        logger.info("Request to change time off status to: " +
                timeOffStatusChangeDto.getStatus() + "with id: " +
                timeOffStatusChangeDto.getDateId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

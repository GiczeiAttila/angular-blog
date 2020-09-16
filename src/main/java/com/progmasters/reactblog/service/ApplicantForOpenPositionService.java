package com.progmasters.reactblog.service;

import com.progmasters.reactblog.controller.OpenPositionController;
import com.progmasters.reactblog.domain.ApplicantForOpenPosition;
import com.progmasters.reactblog.repository.ApplicantForOpenPositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ApplicantForOpenPositionService {
    private static final Logger logger = LoggerFactory.getLogger(OpenPositionController.class);
    private final ApplicantForOpenPositionRepository applicantForOpenPositionRepository;

    public ApplicantForOpenPositionService(ApplicantForOpenPositionRepository applicantForOpenPositionRepository) {
        this.applicantForOpenPositionRepository = applicantForOpenPositionRepository;
    }

    public ApplicantForOpenPosition saveApplicant(ApplicantForOpenPosition applicantForOpenPosition) {
        ApplicantForOpenPosition savedApplication = applicantForOpenPositionRepository.save(applicantForOpenPosition);
        if (savedApplication.getId() != null){
            logger.info("Application saved for user with id:" + savedApplication.getApplicant().getId() +
                    " to open position with id: " + savedApplication.getOpenPosition().getId());
        }
        return savedApplication;
    }
}

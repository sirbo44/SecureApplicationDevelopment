package com.nyc.hosp.controller;

import com.nyc.hosp.model.PatientvisitDTO;
import com.nyc.hosp.service.PatientvisitService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestPatientVisitController {

    private final PatientvisitService patientvisitService;

    public RestPatientVisitController(PatientvisitService patientvisitService) {
        this.patientvisitService = patientvisitService;
    }

    @GetMapping("/allpatientsjson")
    public List<PatientvisitDTO> list(final Model model) {

        return patientvisitService.findAll();
    }

}

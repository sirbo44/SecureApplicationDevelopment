package com.nyc.hosp.model;

import java.time.LocalDate;


public class PatientvisitDTO {

    private Integer visitid;
    private LocalDate vistidate;
    private String diagnosis;
    private Integer patient;
    private Integer doctor;

    public Integer getVisitid() {
        return visitid;
    }

    public void setVisitid(final Integer visitid) {
        this.visitid = visitid;
    }

    public LocalDate getVistidate() {
        return vistidate;
    }

    public void setVistidate(final LocalDate vistidate) {
        this.vistidate = vistidate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(final String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getPatient() {
        return patient;
    }

    public void setPatient(final Integer patient) {
        this.patient = patient;
    }

    public Integer getDoctor() {
        return doctor;
    }

    public void setDoctor(final Integer doctor) {
        this.doctor = doctor;
    }

}

package com.nyc.hosp.repos;

import com.nyc.hosp.domain.Hospuser;
import com.nyc.hosp.domain.Patientvisit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientvisitRepository extends JpaRepository<Patientvisit, Integer> {

    Patientvisit findFirstByPatient(Hospuser hospuser);

    Patientvisit findFirstByDoctor(Hospuser hospuser);

}

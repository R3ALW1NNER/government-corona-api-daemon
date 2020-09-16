package com.realwinner.governmentcoronaapidaemon.domain.repository;

import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCOVID19Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GovernmentCOVID19Repository extends JpaRepository<GovernmentCOVID19Model, Integer> {
    ArrayList<GovernmentCOVID19Model> findByStdDay(String stdDay);
}

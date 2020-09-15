package com.realwinner.governmentcoronaapidaemon.domain.repository;

import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCoronaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentCoronaRepository extends JpaRepository<GovernmentCoronaModel, Integer> {
    GovernmentCoronaModel findByStateDt(String stateDt);
}

package com.demoproject.repo;

import com.demoproject.entity.ReportEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<ReportEnt, Integer> {

}

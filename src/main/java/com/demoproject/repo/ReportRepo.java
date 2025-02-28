package com.demoproject.repo;

import com.demoproject.entity.ReportEnt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<ReportEnt, Integer> {

    List<ReportEnt> findByStatus(boolean status);
}

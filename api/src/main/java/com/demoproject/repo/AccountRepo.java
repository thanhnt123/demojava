package com.demoproject.repo;

import com.demoproject.entity.AccountEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountEnt, Integer> {
    
}

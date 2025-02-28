package com.demoproject.repo;

import com.demoproject.entity.MessageEnt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<MessageEnt, Integer> {

    List<MessageEnt> findByStatus(boolean status);
}

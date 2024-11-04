package com.spring.manshubapi.repository;

import com.spring.manshubapi.entity.Team;
import com.spring.manshubapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, String> {

    List<Team> findAllByUser(User user);

    Team findByJoinCode(String joinCode);
}

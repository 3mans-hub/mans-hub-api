package com.spring.manshubapi.repository;

import com.spring.manshubapi.entity.GroupMember;
import com.spring.manshubapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findAllByUser(User user);
}

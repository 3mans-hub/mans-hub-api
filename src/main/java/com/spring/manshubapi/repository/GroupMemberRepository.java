package com.spring.manshubapi.repository;

import com.spring.manshubapi.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

}

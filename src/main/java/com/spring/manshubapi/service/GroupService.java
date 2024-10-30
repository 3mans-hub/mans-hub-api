package com.spring.manshubapi.service;

import com.spring.manshubapi.dto.response.CreateGroupResponseDto;
import com.spring.manshubapi.dto.response.FindGroupResponseDto;
import com.spring.manshubapi.entity.GroupMember;
import com.spring.manshubapi.entity.Team;
import com.spring.manshubapi.entity.User;
import com.spring.manshubapi.repository.GroupMemberRepository;
import com.spring.manshubapi.repository.TeamRepository;
import com.spring.manshubapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final GroupMemberRepository groupMemberRepository;

    public Team createGroup(CreateGroupResponseDto createGroupResponseDto) {

        User user = userRepository.findByEmail(createGroupResponseDto.getEmail());

        Team newTeam = Team.builder()
                .name(createGroupResponseDto.getGroupName())
                .joinCode(createGroupResponseDto.getEmail())
                .user(user)
                .withdrawal(false)
                .createAt(LocalDateTime.now())
                .build();

        teamRepository.save(newTeam);

        GroupMember groupMember = GroupMember.builder()
                .user(user)
                .team(newTeam)
                .createAt(LocalDateTime.now())
                .withdrawal(false)
                .build();

        groupMemberRepository.save(groupMember);


        return newTeam;
    }

    public List<Team> findGroup(FindGroupResponseDto findGroupResponseDto) {

        User user = userRepository.findByEmail(findGroupResponseDto.getEmail());

        return teamRepository.findAllByUser(user);
    }
}

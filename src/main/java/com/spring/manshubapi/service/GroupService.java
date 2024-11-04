package com.spring.manshubapi.service;

import com.spring.manshubapi.dto.response.CreateGroupResponseDto;
import com.spring.manshubapi.dto.response.FindGroupResponseDto;
import com.spring.manshubapi.dto.response.SignInGroupResponseDto;
import com.spring.manshubapi.entity.GroupMember;
import com.spring.manshubapi.entity.Team;
import com.spring.manshubapi.entity.User;
import com.spring.manshubapi.repository.GroupMemberRepository;
import com.spring.manshubapi.repository.TeamRepository;
import com.spring.manshubapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final GroupMemberRepository groupMemberRepository;

    public Team createGroup(CreateGroupResponseDto createGroupResponseDto) {

        User user = userRepository.findByEmail(createGroupResponseDto.getEmail());

        Team newTeam = Team.builder()
                .name(createGroupResponseDto.getGroupName())
                .joinCode(createGroupResponseDto.getGroupName())
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

    public Team signInGroup(SignInGroupResponseDto signInGroupResponseDto) {
        User user = userRepository.findByEmail(signInGroupResponseDto.getEmail());

        Team joinGroup = teamRepository.findByJoinCode(signInGroupResponseDto.getJoinCode());

        if(joinGroup != null) {
            GroupMember groupMember = GroupMember.builder()
                    .user(user)
                    .team(joinGroup)
                    .createAt(LocalDateTime.now())
                    .withdrawal(false)
                    .build();

            groupMemberRepository.save(groupMember);
        }

        return joinGroup;
    }

    public List<Team> findGroup(FindGroupResponseDto findGroupResponseDto) {

        User user = userRepository.findByEmail(findGroupResponseDto.getEmail());

        List<GroupMember> joinGroupHistory = groupMemberRepository.findAllByUser(user);

        log.info(joinGroupHistory.toString());

        List<Team> joinGroupList= new ArrayList<>();

        for (GroupMember groupMember : joinGroupHistory) {
            joinGroupList.add(groupMember.getTeam());
        }

        return joinGroupList;
    }
}

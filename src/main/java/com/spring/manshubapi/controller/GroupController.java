package com.spring.manshubapi.controller;

import com.spring.manshubapi.dto.response.CreateGroupResponseDto;
import com.spring.manshubapi.dto.response.FindGroupResponseDto;
import com.spring.manshubapi.dto.response.SignInGroupResponseDto;
import com.spring.manshubapi.dto.response.SignInResponseDto;
import com.spring.manshubapi.entity.Team;
import com.spring.manshubapi.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupResponseDto createGroupResponseDto) {

        Team newGroup = groupService.createGroup(createGroupResponseDto);


        return ResponseEntity.ok().body(newGroup);

    }

    @PostMapping
    public ResponseEntity<?> findGroupList(@RequestBody FindGroupResponseDto findGroupResponseDto) {

        List<Team> teamList = groupService.findGroup(findGroupResponseDto);

        return ResponseEntity.ok().body(teamList);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<?> signIn(@RequestBody SignInGroupResponseDto signInGroupResponseDto) {

        Team joinGroup = groupService.signInGroup(signInGroupResponseDto);

        return ResponseEntity.ok().body(joinGroup);
    }
}

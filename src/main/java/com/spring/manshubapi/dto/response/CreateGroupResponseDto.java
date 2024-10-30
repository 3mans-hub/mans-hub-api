package com.spring.manshubapi.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupResponseDto {

    String groupName;
    String email;
}

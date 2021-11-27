package com.iot.server.common.dto;


import com.iot.server.common.enums.AuthorityEnum;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseDto {

  private String email;
  private String firstName;
  private String lastName;
  private AuthorityEnum authority;
}


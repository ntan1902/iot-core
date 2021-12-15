package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.CreateUserRequest;
import com.iot.server.application.controller.response.CreateUserResponse;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import com.iot.server.common.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class CreateUserHandler extends BaseHandler<CreateUserRequest, CreateUserResponse> {

    @Override
    protected void validate(CreateUserRequest request) throws IoTException {
        validateNotNull("authorities", request.getAuthorities());
        validateAuthorities("authorities", request.getAuthorities());
    }

    @Override
    protected CreateUserResponse processRequest(final CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();

        SecurityUser currentUser = getCurrentUser();
        checkPermission(currentUser.getAuthorities(), request.getAuthorities());

        final UserDto user = getUserFromRequest(request, currentUser);
        getTenantIdAndCustomerId(currentUser, user);

        UserDto savedUser = userService.saveUserWithAuthorities(user, request.getAuthorities());
        response.setUserId(savedUser.getId());
        return response;
    }

    private void checkPermission(Collection<GrantedAuthority> currentAuthorities, List<String> requestAuthorities) {

        if (isTenant(currentAuthorities)
                && isAdmin(requestAuthorities)) {
            throw new IoTException(ReasonEnum.PERMISSION_DENIED, "You don't have permission to create admin user");
        }

        if (isCustomer(currentAuthorities)
                && (isAdmin(requestAuthorities) || isTenant(requestAuthorities))) {
            throw new IoTException(ReasonEnum.PERMISSION_DENIED, "You don't have permission to create admin or tenant user");
        }
    }

    private void getTenantIdAndCustomerId(SecurityUser currentUser, UserDto user) {

        if (isTenant(currentUser.getAuthorities())) {
            user.setTenantId(currentUser.getId());
        }

        if (isCustomer(currentUser.getAuthorities())) {
            user.setCustomerId(currentUser.getId());

            if (user.getTenantId() == null) {
                user.setTenantId(currentUser.getTenantId());
            }
        }
    }

    private UserDto getUserFromRequest(CreateUserRequest request, SecurityUser currentUser) {
        return UserDto.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createUid(currentUser.getId())
                .build();
    }
}

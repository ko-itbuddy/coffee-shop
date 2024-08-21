package org.itbuddy.coffeeshop.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.user.application.UserService;
import org.itbuddy.coffeeshop.user.controller.request.PutUserChargePointRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController implements UserControllerDoc {

    private final UserService userService;

    public static final String API_PUT_USER_POINT = "/api/user/point";
    @Override
    @PutMapping(API_PUT_USER_POINT)
    public ApiResponse<Object> userPoint(
        @Valid @RequestBody PutUserChargePointRequest putUserChargePointRequest) {
        return ApiResponse.ok(null);
    }
}

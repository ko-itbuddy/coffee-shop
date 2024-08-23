package org.itbuddy.coffeeshop.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.user.application.UserService;
import org.itbuddy.coffeeshop.user.controller.request.PutUserChargePointRequest;
import org.itbuddy.coffeeshop.user.controller.response.PutUserChargePointResponse;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController implements UserControllerDoc {

    private final UserService userService;

    public static final String API_PATCH_USER_POINT = "/api/user/point";
    @Override
    @PatchMapping(API_PATCH_USER_POINT)
    public ApiResponse<PutUserChargePointResponse> userPoint(
        @Valid @RequestBody PutUserChargePointRequest putUserChargePointRequest) throws Exception {
        return ApiResponse.ok(
            new PutUserChargePointResponse(
                userService.chargePoint(putUserChargePointRequest.getId(),
                    putUserChargePointRequest.getPoint())
            )
        );
    }
}

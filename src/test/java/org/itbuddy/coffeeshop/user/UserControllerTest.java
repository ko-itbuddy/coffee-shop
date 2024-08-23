package org.itbuddy.coffeeshop.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itbuddy.coffeeshop.user.application.UserService;
import org.itbuddy.coffeeshop.user.controller.UserController;
import org.itbuddy.coffeeshop.user.controller.request.PutUserChargePointRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Nested
    @DisplayName("사용자 포인트 충전")
    class ChargePoint {

        @Test
        @DisplayName("정상 요청")
        void success() throws Exception {
            // given
            PutUserChargePointRequest request = PutUserChargePointRequest.builder()
                                                                         .id(1L)
                                                                         .point(1000)
                                                                         .build();
            // when
            // then
            mockMvc.perform(
                       patch(UserController.API_PATCH_USER_POINT)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isOk());
        }

        @Test
        @DisplayName("아이디 음수")
        void withMinusId() throws Exception {
            // given
            PutUserChargePointRequest request = PutUserChargePointRequest.builder()
                                                                         .id(-1L)
                                                                         .point(1000)
                                                                         .build();
            // when
            // then
            mockMvc.perform(
                       patch(UserController.API_PATCH_USER_POINT)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("사용자 아이디는 양수입니다."));
        }

        @Test
        @DisplayName("아이디 null")
        void withNullId() throws Exception {
            // given
            PutUserChargePointRequest request = PutUserChargePointRequest.builder()
                                                                         .id(null)
                                                                         .point(1000)
                                                                         .build();
            // when
            // then
            mockMvc.perform(
                       patch(UserController.API_PATCH_USER_POINT)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("사용자 아이디는 필수값입니다."));
        }

        @Test
        @DisplayName("포인트 음수")
        void withMinusPoint() throws Exception {
            // given
            PutUserChargePointRequest request = PutUserChargePointRequest.builder()
                                                                         .id(1L)
                                                                         .point(-1000)
                                                                         .build();
            // when
            // then
            mockMvc.perform(
                       patch(UserController.API_PATCH_USER_POINT)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("충전 포인트는 양수입니다."));
        }

        @Test
        @DisplayName("포인트 null")
        void withNullPoint() throws Exception {
            // given
            PutUserChargePointRequest request = PutUserChargePointRequest.builder()
                                                                         .id(1L)
                                                                         .point(null)
                                                                         .build();
            // when
            // then
            mockMvc.perform(
                       patch(UserController.API_PATCH_USER_POINT)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("충전 포인트는 필수값입니다."));
        }


    }

}

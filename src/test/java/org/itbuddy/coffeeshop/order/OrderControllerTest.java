package org.itbuddy.coffeeshop.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itbuddy.coffeeshop.order.application.OrderService;
import org.itbuddy.coffeeshop.order.application.PopularMenuService;
import org.itbuddy.coffeeshop.order.controller.OrderController;
import org.itbuddy.coffeeshop.order.controller.request.PostOrderRequest;
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
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService menuService;

    @MockBean
    private PopularMenuService popularMenuService;

    @Nested
    @DisplayName("주문")
    class PostOrder {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            // given
            PostOrderRequest request = PostOrderRequest.builder()
                                                       .userId(1L)
                                                       .menuId(1L)
                                                       .build();

            // when
            // then
            mockMvc.perform(
                       post(OrderController.API_POST_ORDER)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.code").value("200"))
                   .andExpect(jsonPath("$.status").value("OK"));
        }

        @Test
        @DisplayName("아이디 음수")
        void withMinusId() throws Exception {
            // given
            PostOrderRequest request = PostOrderRequest.builder()
                                                       .userId(-1L)
                                                       .menuId(1L)
                                                       .build();
            // when
            // then
            mockMvc.perform(
                       post(OrderController.API_POST_ORDER)
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
            PostOrderRequest request = PostOrderRequest.builder()
                                                       .userId(null)
                                                       .menuId(1L)
                                                       .build();
            // when
            // then
            mockMvc.perform(
                       post(OrderController.API_POST_ORDER)
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
        @DisplayName("메뉴 음수")
        void withMinusMenu() throws Exception {
            // given
            PostOrderRequest request = PostOrderRequest.builder()
                                                       .userId(1L)
                                                       .menuId(-1L)
                                                       .build();
            // when
            // then
            mockMvc.perform(
                       post(OrderController.API_POST_ORDER)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("주문 메뉴 아이디는 양수입니다."));
        }

        @Test
        @DisplayName("메뉴 null")
        void withNullMenu() throws Exception {
            // given
            PostOrderRequest request = PostOrderRequest.builder()
                                                       .userId(1L)
                                                       .menuId(null)
                                                       .build();
            // when
            // then
            mockMvc.perform(
                       post(OrderController.API_POST_ORDER)
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("주문 메뉴 아이디는 필수값입니다."));
        }

    }

    @Nested
    @DisplayName("인기 메뉴 조회")
    class GetPopularMenu {
        @Test
        @DisplayName("성공")
        void success() throws Exception {
            // given
            // when
            // then
            mockMvc.perform(
                       get(OrderController.API_GET_POPULAR_MENUS)
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.code").value("200"))
                   .andExpect(jsonPath("$.status").value("OK"));
        }
    }

}

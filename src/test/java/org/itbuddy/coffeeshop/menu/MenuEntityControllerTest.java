package org.itbuddy.coffeeshop.menu;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itbuddy.coffeeshop.menu.application.MenuService;
import org.itbuddy.coffeeshop.menu.controller.MenuController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

@ActiveProfiles("test")
@WebMvcTest(controllers = MenuController.class)
public class MenuEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuService menuService;

    @Nested
    @DisplayName("메뉴 목록 조회")
    class getMenus {

        @Test
        @DisplayName("메뉴 목록 조회")
        void success() throws Exception {
            // given

            // when
            // then
            mockMvc.perform(
                       get("/api/menus")
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(status().isOk());
        }
    }

}

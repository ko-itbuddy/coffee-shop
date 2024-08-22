package org.itbuddy.coffeeshop.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.itbuddy.coffeeshop.config.TestContainerConfig;
import org.itbuddy.coffeeshop.menu.application.MenuDto;
import org.itbuddy.coffeeshop.menu.application.MenuService;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;
import org.itbuddy.coffeeshop.menu.domain.MenuRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(TestContainerConfig.class)
@ContextConfiguration(initializers = TestContainerConfig.IntegrationTestInitializer.class)

class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    void init() throws Exception {

        MenuEntity menu1 = createMenuEntity("아이스 아메리카노", 1500);
        MenuEntity menu2 = createMenuEntity("더치 아메리카노", 2500);
        MenuEntity menu3 = createMenuEntity("아이스티", 3000);
        MenuEntity menu4 = createMenuEntity("카페라떼", 4500);

        menuRepository.saveAll(List.of(menu1, menu2, menu3, menu4));
    }

    @AfterEach
    void tearDown() throws Exception {
        menuRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("1. 커피 메뉴 목록 조회")
    class GetMenus {

        @Test
        @DisplayName("정상")
        void success() throws Exception {
            // given

            // when
            List<MenuDto> menus = menuService.getMenus();
            // then
            assertThat(menus).hasSize(4);
            assertThat(menus).extracting("id", "name", "price")
                             .containsExactly(
                                 tuple(1L, "아이스 아메리카노", 1500),
                                 tuple(2L, "더치 아메리카노", 2500),
                                 tuple(3L, "아이스티", 3000),
                                 tuple(4L, "카페라떼", 4500)
                             );
        }
    }


    private MenuEntity createMenuEntity(String name, Integer price) {
        return MenuEntity.builder()
                         .name(name)
                         .price(price)
                         .build();
    }

}

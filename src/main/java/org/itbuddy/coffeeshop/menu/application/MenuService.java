package org.itbuddy.coffeeshop.menu.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;
import org.itbuddy.coffeeshop.menu.domain.MenuRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuDto> getMenus() {
        return menuRepository.findAll()
                             .stream()
                             .map(MenuDto::of)
                             .collect(Collectors.toList());
    }
}

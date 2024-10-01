package org.itbuddy.coffeeshop.user.application;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserPointTransactionEntity;

@Getter
@Schema(description = "사용자")
public class UserDto {

    @Schema(description = "사용자 식별자(아이디)")
    private final Long id;
    @Schema(description = "사용자 이름")
    private String name;
    @Schema(description = "현재 포인트")
    private final Integer point;

    @Builder
    private UserDto(Long id, String name, Integer point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }



    public UserEntity toEntity() {
        return UserEntity.builder()
                      .id(this.getId())
                      .name(this.getName())
                      .point(this.getPoint())
                      .build();
    }

    public static UserDto of(UserEntity entity) {
        return UserDto.builder()
                      .id(entity.getId())
                      .name(entity.getName())
                      .point(entity.getPoint())
                      .build();
    }

}

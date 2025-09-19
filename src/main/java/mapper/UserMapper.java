package mapper;

import dto.UserDto;
import entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto dto);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "roleId", source = "role.roleId")
    UserDto toDto(User entity);
}

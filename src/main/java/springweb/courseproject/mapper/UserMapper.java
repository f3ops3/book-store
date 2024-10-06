package springweb.courseproject.mapper;

import org.mapstruct.Mapper;
import springweb.courseproject.config.MapperConfig;
import springweb.courseproject.dto.user.UserRegistrationRequestDto;
import springweb.courseproject.dto.user.UserResponseDto;
import springweb.courseproject.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);
}

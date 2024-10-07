package springweb.courseproject.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springweb.courseproject.dto.user.UserRegistrationRequestDto;
import springweb.courseproject.dto.user.UserResponseDto;
import springweb.courseproject.exception.RegistrationException;
import springweb.courseproject.mapper.UserMapper;
import springweb.courseproject.model.User;
import springweb.courseproject.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException("User with email: "
                    + userRegistrationRequestDto.getEmail() + " already exists");
        }
        User user = userMapper.toUser(userRegistrationRequestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}

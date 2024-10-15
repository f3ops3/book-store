package springweb.courseproject.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springweb.courseproject.dto.user.UserRegistrationRequestDto;
import springweb.courseproject.dto.user.UserResponseDto;
import springweb.courseproject.exception.RegistrationException;
import springweb.courseproject.mapper.UserMapper;
import springweb.courseproject.model.Role;
import springweb.courseproject.model.User;
import springweb.courseproject.repository.role.RoleRepository;
import springweb.courseproject.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException("User with email: "
                    + userRegistrationRequestDto.getEmail() + " already exists");
        }
        User user = userMapper.toEntity(userRegistrationRequestDto);
        user.setRoles(Set.of(roleRepository.findByRole(Role.RoleName.ROLE_USER)));
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }
}

package hr.webshop.service;

import hr.webshop.dto.UserDto;
import hr.webshop.irepository.UserRepository;
import hr.webshop.iservice.UserService;
import hr.webshop.mapper.AppMapper;
import hr.webshop.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final AppMapper appMapper;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, AppMapper appMapper, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appMapper = appMapper;
        this.userRepository = userRepository;
    }


    @Override
    public void register(UserDto userDto) {
        User user = appMapper.toUserEntity(userDto);
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        appMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserByUsername(String username) {
       return appMapper.toUserDto(userRepository.findByUsername(username));
    }
}

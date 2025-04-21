package hr.webshop.iservice;

import hr.webshop.dto.UserDto;

public interface UserService {
void register(UserDto userDto);
UserDto getUserByUsername(String username);
}

package hr.webshop.iservice;

import hr.webshop.form.UserForm;
import hr.webshop.dto.UserDto;

public interface UserService {

UserDto register(UserDto userDto);
UserDto getUserByUsername(String username);
}

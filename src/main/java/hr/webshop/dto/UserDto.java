package hr.webshop.dto;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Data
public class UserDto {
    private Integer id;
    @NotEmpty(message = "This field cannot be empty")
    private String username;
    @NotEmpty(message = "This field cannot be empty")
    private String firstname;
    @NotEmpty(message = "This field cannot be empty")
    private String lastname;
    @NotEmpty(message = "Enter you address")
    private String address;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private String role;
}

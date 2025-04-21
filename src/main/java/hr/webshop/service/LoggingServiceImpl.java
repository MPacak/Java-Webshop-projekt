package hr.webshop.service;

import hr.webshop.dto.LoginDto;
import hr.webshop.irepository.LoggingRepository;
import hr.webshop.irepository.UserRepository;
import hr.webshop.iservice.LoggingService;
import hr.webshop.mapper.AppMapper;
import hr.webshop.model.LoginLogs;
import hr.webshop.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoggingServiceImpl implements LoggingService {

private final LoggingRepository logingRepository;
private final UserRepository userRepository;
    private final AppMapper appMapper;

    @Override
    public void logLogin(Integer id, String ip, LocalDateTime time) {
        User user = userRepository.findById(id).get();
        logingRepository.save(new LoginLogs(user, ip, time));
    }

    @Override
    public List<LoginDto> getAllLoginLogs() {

        return logingRepository.findAll()
                .stream()
                .map(appMapper::toLoginDto)
                .toList();
    }
}

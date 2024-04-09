package setecolinas.com.webflux.model.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import setecolinas.com.webflux.entity.User;
import setecolinas.com.webflux.model.mapper.UserMapper;
import setecolinas.com.webflux.model.repository.UserRepository;
import setecolinas.com.webflux.model.request.UserRequest;
import setecolinas.com.webflux.model.response.UserResponse;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Mono<User> save(final UserRequest request){
        return this.userRepository.save(this.userMapper.toEntity(request));
    }

    public Mono<UserResponse> findById(final String id){
        return this.userRepository.findById(id);
    }
}

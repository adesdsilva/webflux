package setecolinas.com.webflux.model.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import setecolinas.com.webflux.entity.User;
import setecolinas.com.webflux.model.mapper.UserMapper;
import setecolinas.com.webflux.model.repository.UserRepository;
import setecolinas.com.webflux.model.request.UserRequest;
import setecolinas.com.webflux.model.service.exception.ObjectNotFoundException;

import static java.lang.String.format;

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

    public Mono<User> findById(final String id){
        return this.handlerNotFound(
                this.userRepository
                        .findById(id), id);
    }

    public Flux<User> findAll(){
        return this.userRepository.findAll();
    }

    public Mono<User> update(final String id, final UserRequest request){
        return this.findById(id)
                .map(entity -> userMapper.toEntity(request, entity))
                .flatMap(userRepository::save);
    }

    public Mono<User> delete(final String id){
        return this.handlerNotFound(
                this.userRepository
                        .findAndRemove(id), id);
    }

    private <T> Mono<T> handlerNotFound(Mono<T> mono, String id){
        return mono.switchIfEmpty(Mono.error(
                new ObjectNotFoundException(
                        format("Object not found. Id: %s, Type: %s",
                                id, User.class.getSimpleName())
                )
        ));
    }
}

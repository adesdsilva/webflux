package setecolinas.com.webflux.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import setecolinas.com.webflux.entity.User;
import setecolinas.com.webflux.model.mapper.UserMapper;
import setecolinas.com.webflux.model.repository.UserRepository;
import setecolinas.com.webflux.model.request.UserRequest;
import setecolinas.com.webflux.model.service.exception.ObjectNotFoundException;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @Test
    void testSave() {
        final UserRequest userRequest =
                new UserRequest("Adelino Silva",
                        "adelino@email.com",
                        "54321");
        User entity = User.builder().build();
        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(this.userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.save(userRequest);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        verify(this.userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindById() {
        when(this.userRepository.findById(anyString())).thenReturn(Mono.just(User.builder().id("1235").build()));
        Mono<User> result = userService.findById("1235");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class &&
                        Objects.nonNull(user) &&
                        user.getId().equals("1235"))
                .expectComplete()
                .verify();

        verify(this.userRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        when(this.userRepository.findAll()).thenReturn(Flux.just(User.builder().build()));
        Flux<User> result = userService.findAll();

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class )
                .expectComplete()
                .verify();

        verify(this.userRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        final UserRequest userRequest =
                new UserRequest("Adelino Silva",
                        "adelino@email.com",
                        "54321");
        User entity = User.builder().build();
        when(userMapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(entity);
        when(this.userRepository.findById(anyString())).thenReturn(Mono.just(entity));
        when(this.userRepository.save(any(User.class))).thenReturn(Mono.just(entity));

        Mono<User> result = userService.update("123", userRequest);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        verify(this.userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDelete() {
        User entity = User.builder().build();
        when(this.userRepository.findAndRemove(anyString())).thenReturn(Mono.just(entity));

        Mono<User> result = userService.delete("123");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        verify(this.userRepository, times(1)).findAndRemove(anyString());
    }

    @Test
    void testHandlerNotFound() {
        when(this.userRepository.findById(anyString())).thenReturn(Mono.empty());

        try{
            userService.findById("123").block();
        } catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals(format("Object not found. Id: %s, Type: %s",
                    "123", User.class.getSimpleName()), e.getMessage());
        }
    }
}
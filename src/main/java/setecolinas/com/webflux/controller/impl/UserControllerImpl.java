package setecolinas.com.webflux.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import setecolinas.com.webflux.controller.UserController;
import setecolinas.com.webflux.model.mapper.UserMapper;
import setecolinas.com.webflux.model.request.UserRequest;
import setecolinas.com.webflux.model.response.UserResponse;
import setecolinas.com.webflux.model.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserControllerImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<Mono<Void>> save(UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.save(request)
                        .then());
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> findById(String id) {
        return ResponseEntity
                .ok()
                .body(this.userService.findById(id)
                .map(this.userMapper::toResponse));
    }

    @Override
    public ResponseEntity<Flux<UserResponse>> findAll() {
        return ResponseEntity.ok().body(
                this.userService.findAll().map(userMapper::toResponse));
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
        return ResponseEntity.ok().body(
                this.userService.update(id, request)
                        .map(userMapper::toResponse));
    }

    @Override
    public ResponseEntity<Mono<Void>> delete(String id) {
        return ResponseEntity.ok().body(
                this.userService.delete(id).then());
    }
}

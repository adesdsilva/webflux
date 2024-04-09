package setecolinas.com.webflux.model.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import setecolinas.com.webflux.entity.User;
import setecolinas.com.webflux.model.response.UserResponse;

@Repository
public class UserRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public UserRepository(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<User> save(final User user){
        return this.mongoTemplate.save(user);
    }

    public Mono<UserResponse> findById(final String id){
        return this.mongoTemplate.findById(id, UserResponse.class);
    }
}

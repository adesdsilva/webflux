package setecolinas.com.webflux.model.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import setecolinas.com.webflux.entity.User;

@Repository
public class UserRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public UserRepository(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<User> save(final User user){
        return this.mongoTemplate.save(user);
    }

    public Mono<User> findById(final String id){
        return this.mongoTemplate.findById(id, User.class);
    }
}

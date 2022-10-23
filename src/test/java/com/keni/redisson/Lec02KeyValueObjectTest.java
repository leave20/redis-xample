package com.keni.redisson;

import org.example.dto.User;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec02KeyValueObjectTest extends BaseTest {

    @Test
    public void keyValueObjectTest() {
     /*   User user = new User("Ken", 30, "Seoul", List.of(1, 2, 3));
        RBucketReactive<User> bucket = this.redissonReactiveClient.getBucket("user:1", new TypedJsonJacksonCodec(User.class));
        Mono<Void> set = bucket.set(user);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(Mono.when(set, get))
                .verifyComplete();*/
    }
}

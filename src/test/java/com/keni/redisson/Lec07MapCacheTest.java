package com.keni.redisson;

import org.example.dto.User;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapCacheRx;
import org.redisson.api.RMapReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec07MapCacheTest extends BaseTest {
    @Test
    public void mapCacheTest() {
        TypedJsonJacksonCodec mapcodec = new TypedJsonJacksonCodec(Integer.class, User.class);
        RMapCacheReactive<Integer, User> mapCacheReactive = this.redissonReactiveClient.getMapCache("users:cache", mapcodec);

        User user = new User("Ken", 30, "Seoul", List.of(1, 2, 3));
        User user2 = new User("Ken2", 30, "Seoul", List.of(1, 2, 3));

        Mono<User> st1 = mapCacheReactive.put(1, user, 10, TimeUnit.SECONDS);

        Mono<User> st2 = mapCacheReactive.put(2, user2, 10, TimeUnit.SECONDS);

        StepVerifier.create(st1.then(st2).then())
                .verifyComplete();

        sleep(5000);

        mapCacheReactive.get(1)
                .doOnNext(System.out::println)
                .subscribe();

        mapCacheReactive.get(2)
                .doOnNext(System.out::println)
                .subscribe();

        sleep(5000);

        mapCacheReactive.get(1)
                .doOnNext(System.out::println)
                .subscribe();

        mapCacheReactive.get(2)
                .doOnNext(System.out::println)
                .subscribe();

    }
}

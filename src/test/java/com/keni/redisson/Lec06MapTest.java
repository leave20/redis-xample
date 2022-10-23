package com.keni.redisson;

import org.example.dto.User;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class Lec06MapTest extends BaseTest {

    @Test
    public void mapTest() {
        RMapReactive<String, String> map = this.redissonReactiveClient.getMap("user:3", StringCodec.INSTANCE);
        Mono<Void> mono = map.fastPut("name", "Ken")
                .then(map.fastPut("age", "30"))
                .then(map.fastPut("city", "Seoul"))
                .doOnNext(System.out::println)
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    public void mapTwoTest() {
        RMapReactive<String, String> map = this.redissonReactiveClient.getMap("user:4", StringCodec.INSTANCE);
        Map<String, String> javaMap = Map.of(
                "name", "Ken",
                "age", "30",
                "city", "Seoul");
        Mono<Void> mono = map.putAll(javaMap)
                .doOnNext(System.out::println)
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    public void mapThreeTest() {
        RMapReactive<String, String> map = this.redissonReactiveClient.getMap("user:5", StringCodec.INSTANCE);
        Map<String, String> javaMap = Map.of(
                "name", "Ken",
                "age", "30",
                "city", "Seoul");

        StepVerifier.create(map.putAll(javaMap).then())
                .verifyComplete();
    }

    @Test
    public void mapFourTest() {
        TypedJsonJacksonCodec typedJsonJacksonCodec = new TypedJsonJacksonCodec(Integer.class, User.class);
        RMapReactive<Integer, User> map = this.redissonReactiveClient.getMap("users", typedJsonJacksonCodec);
        Map<Integer, User> javaMap = Map.of(
                1, new User("Ken", 30, "Seoul", List.of(1, 2, 3)),
                2, new User("Greg", 12, "Tokio", List.of(1, 2, 3)),
                3, new User("Elias", 24, "Taiwan", List.of(1, 2, 3))
        );

        StepVerifier.create(map.putAll(javaMap).then())
                .verifyComplete();
    }


}

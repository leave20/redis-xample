package com.keni.redisson;

import org.example.config.RedissonConfig;
import org.example.dto.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class Lec08LocalCacheMapTest extends BaseTest {

    RLocalCachedMap<Integer, User> user;

    @BeforeAll
    public void setupClient() {
        RedissonConfig redissonConfig = new RedissonConfig();
        RedissonClient redissonClient = redissonConfig.getRedissonClient();

        LocalCachedMapOptions<Integer, User> options = LocalCachedMapOptions.<Integer, User>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);

        user = redissonClient.getLocalCachedMap("user", new TypedJsonJacksonCodec(Integer.class, User.class), options);

    }

    @Test
    public void appServer1() {
        User user1 = new User("Ken", 30, "Seoul", List.of(1, 2, 3));
        user.put(1, user1);
        System.out.println(user.get(1));

        User user2 = new User("Ken2", 30, "Seoul", List.of(1, 2, 3));
        user.put(2, user2);
        System.out.println(user.get(2));

        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println(user.get(1)))
                .subscribe();

        sleep(50_000);
    }

    @Test
    public void appServer2() {
        User user1 = new User("Ken-update", 60, "Tokio", List.of(1, 2, 3));
        user.put(1, user1);
        System.out.println(user.get(1));


        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println(user.get(1)))
                .subscribe();
        sleep(50_000);
    }

    @Test
    public void appServer3() {
        User user1 = new User("Ken-update-again", 90, "Taiwan", List.of(1, 2, 3));
        user.put(1, user1);
        System.out.println(user.get(1));

        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println(user.get(1)))
                .subscribe();
    }

}


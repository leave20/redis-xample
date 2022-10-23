package com.keni.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;


@Slf4j
public class Lec01KeyValueTest extends BaseTest {
    @Test
    public void keyValueAccessTest() {
        RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("Ken");
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(Mono.when(set, get))
                .verifyComplete();
    }

    @Test
    public void keyValueExpireTest2() throws InterruptedException {
        RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("Ken", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(Mono.when(set, get))
                .verifyComplete();
    }

    @Test
    public void keyValueExtendExpireTest() {
        RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("Ken", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(Mono.when(set, get))
                .verifyComplete();

        //extend expire
        sleep(5000);
        Mono<Boolean> mono = bucket.expire(60, TimeUnit.SECONDS);
        StepVerifier.create(mono)
                .expectNext(true)
                .verifyComplete();

        Mono<Void> ttl = bucket.remainTimeToLive()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(ttl)
                .verifyComplete();
    }
}

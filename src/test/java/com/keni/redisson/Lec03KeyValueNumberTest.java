package com.keni.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec03KeyValueNumberTest extends BaseTest {

    @Test
    public void keyValueIncreaseTest(){

        //set k v -- incre, decr
        RAtomicLongReactive atomicLong = this.redissonReactiveClient.getAtomicLong("user:1:visit");
        Mono<Void> mono= Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .flatMap(i -> atomicLong.incrementAndGet())
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
    }
}

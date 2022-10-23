package com.keni.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RListReactive;
import org.redisson.api.RQueueReactive;
import org.redisson.api.RQueueRx;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Lec09ListQueueStackTest extends BaseTest {

    @Test
    public void listTest() {

        RListReactive<Long> list = this.redissonReactiveClient.getList("number-input", LongCodec.INSTANCE);
//        Mono<Void> listAdd = Flux.range(1, 10)
//                .map(i -> (long) i)
//                .flatMap(list::add)
//                .then();

        List<Long> longList = LongStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList());

        StepVerifier.create(list.addAll(longList).then())
                .verifyComplete();

        StepVerifier.create(list.size())
                .expectNext(46)
                .verifyComplete();
    }

    @Test
    public void listTwoTest() {

        RQueueReactive<Long> queue = this.redissonReactiveClient.getQueue("number-input", LongCodec.INSTANCE);

        Mono<Void> listAdd = queue.poll()
                .repeat(3)
                .doOnNext(System.out::println)
                .then();

        StepVerifier.create(listAdd)
                .verifyComplete();

        StepVerifier.create(queue.size())
                .expectNext(6)
                .verifyComplete();
    }

}

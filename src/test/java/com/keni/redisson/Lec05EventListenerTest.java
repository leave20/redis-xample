package com.keni.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

public class Lec05EventListenerTest extends BaseTest {

    /**
     * Manejado por eventos de expired
     */

    @Test
    public void expiredEventTest() {

        /*
         * En este metodo setearemos un valor a "user:1:name" el cual solo durará 5 segundos.
         * Mostrará el dato que hemos seteado.
         * Luego esperamos 10 segundos durante los cuales la cache se encontrará expirada.
         * Luego mostrará un "user:1:name" caducado o vacío.
         */

        RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("Ken", 5, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        Mono<Void> event = bucket.addListener(new ExpiredObjectListener() {
            @Override
            public void onExpired(String s) {
                System.out.println("Expired => " + s);
            }
        }).then();

        StepVerifier.create(Mono.when(set, get, event))
                .verifyComplete();

        //wait 10sec
        sleep(10_000);
    }

    /**
     * Manejado por eventos de deleted
     */


    @Test
    public void deleteEventTest() {

        /*
         * En este mètodo setearemos un valor a "user:1:name".
         * Luego Mostrarà dicho valor y le colocaremos un sleep de 1 minuto.
         * Dentro de ese minuto eliminaremos el valor de "user:1:name" de forma manual.
         * Terminando el sleep, mostrará un "user:1:name" eliminado o vacío.
         * Tambien se podrà eliminar cuando termine el mètodo, el cual es luego que termine el mètodo.
         */

        RBucketReactive<String> bucket = this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("Ken");
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();

        Mono<Void> event = bucket.addListener(new DeletedObjectListener() {
            @Override
            public void onDeleted(String s) {
                System.out.println("Deleted => " + s);
            }
        }).then();

        StepVerifier.create(Mono.when(set, get, event))
                .verifyComplete();

        sleep(60_000);
    }
}

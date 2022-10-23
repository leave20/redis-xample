package com.keni.redisson;

import org.example.config.RedissonConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonReactiveClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private final RedissonConfig redissonConfig = new RedissonConfig();
    protected RedissonReactiveClient redissonReactiveClient;


    /**
     * @BeforeAll Esta anotacion ejecuta un método antes de todos los test.
     * Esto es útil para inicializar recursos que se utilizarán en todos los test.
     * La anotación se utiliza normalmente en métodos estáticos.
     * La anotación se utiliza normalmente para inicializar varias cosas para las pruebas.
     */
    @BeforeAll
    public void setClient() {
        this.redissonReactiveClient = this.redissonConfig.getRedissonReactiveClient();
    }


    /**
     * @AfterAll La anotación se utiliza para ejecutar el método anotado. Solo despues de que se haya ejecutado todas las pruebas.
     * Utilizamos esta anotación para derribar o finalizar todos los procesos al final de todas las pruebas .
     */
    @AfterAll
    public void shutdown() {
        this.redissonReactiveClient.shutdown();
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

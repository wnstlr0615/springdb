package com.example.springdb.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class UncheckedTest {
    @Test
    void unchecked_catch(){
        Service service = new Service();
        service.callCatch();
    }
    @Test
    void unchecked_throw(){
        Service service = new Service();
        assertThrows(MyUncheckedException.class,
                service::callThrow);
    }


    public static class MyUncheckedException extends RuntimeException {

    }

    public static class Service{
        public void callCatch(){
            Repository repository = new Repository();
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리, message = {}", e.getMessage(), e);

            }
        }
        public void callThrow(){
            Repository repository = new Repository();
            repository.call();
        }
    }
    public static class Repository{
        public void call(){
            throw new MyUncheckedException();
        }
    }
}

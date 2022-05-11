package com.example.springdb.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch(){
        Service service = new Service();
        service.callCatch();
    }
    @Test
    void checked_throw(){
        Service service = new Service();
        assertThrows(MyCheckedException.class,
                service::callThrow);
    }


    /**
     * Exception을 상속받는 예외는 체크 예외가 된다.
     * */
    static class MyCheckedException extends Exception{

    }


    static class Service{
        public void callCatch(){
            Repository repository = new Repository();
            /**
             * 예외를 잡아서 처리하는 코드
             * */
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외 처리, message = {}", e.getMessage(), e);
            }
        }
        public void callThrow() throws MyCheckedException {
            Repository repository = new Repository();
            repository.call();
        }
    }
    static class Repository{
        public void call() throws MyCheckedException {
            throw new MyCheckedException();
        }
    }
}

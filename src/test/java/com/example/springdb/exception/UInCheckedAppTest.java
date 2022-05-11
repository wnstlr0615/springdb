package com.example.springdb.exception;

import java.net.ConnectException;
import java.sql.SQLException;

public class UInCheckedAppTest {

    static class Controller{
        Service service = new Service();
        public void request()  {
            service.logic();
        }
    }
    static class Service{
        NetworkClient networkClient = new NetworkClient();
        Repository repository = new Repository();
        public void logic()  {
            networkClient.call();
            try {
                repository.call();
            } catch (SQLException e) {
                throw new RuntimeSqlException(e);
            }
        }
    }
    static class NetworkClient{
        public void call()  {
            try {
                throw new ConnectException("연결 실패");
            } catch (ConnectException e) {
                throw new RuntimeSqlException(e.getMessage());
            }
        }
    }
    static class Repository{
        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }
    static class RuntimeConnectException extends RuntimeException{
        public RuntimeConnectException(String message) {
            super(message);
        }
    }
    static class RuntimeSqlException extends RuntimeException{
        public RuntimeSqlException(String message) {
            super(message);
        }

        public RuntimeSqlException(Throwable cause) {
            super(cause);
        }
    }
}

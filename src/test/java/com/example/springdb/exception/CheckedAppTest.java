package com.example.springdb.exception;

import java.net.ConnectException;
import java.sql.SQLException;

public class CheckedAppTest {

    static class Controller{
        Service service = new Service();
        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }
    static class Service{
        NetworkClient networkClient = new NetworkClient();
        Repository repository = new Repository();
        public void logic() throws ConnectException, SQLException {
            networkClient.call();
            repository.call();
        }
    }
    static class NetworkClient{
        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }
    static class Repository{
        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }
}

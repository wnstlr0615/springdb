package com.example.springdb.repository;

import com.example.springdb.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.example.springdb.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {
    MemberRepositoryV1 repositoryV1;

   /* @BeforeEach
    void beforeEach() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        repositoryV1 = new MemberRepositoryV1(dataSource);
    }*/

     @BeforeEach
    void beforeEach() throws Exception {
        HikariDataSource dataSource = new HikariDataSource();
         dataSource.setJdbcUrl(URL);
         dataSource.setUsername(USERNAME);
         dataSource.setPassword(PASSWORD);
         dataSource.setMaximumPoolSize(10);
         dataSource.setPoolName("MyPool");
        repositoryV1 = new MemberRepositoryV1(dataSource);
    }
    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV4", 100000);
        repositoryV1.save(member);

        //findById
        Member findMember = repositoryV1.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(member).isEqualTo(findMember);

        repositoryV1.update(findMember.getMemberId(), 20000);
        Member updateMember = repositoryV1.findById(findMember.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        repositoryV1.delete(member.getMemberId());
        assertThatThrownBy(() -> repositoryV1.findById(findMember.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
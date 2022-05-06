package com.example.springdb.repository;

import com.example.springdb.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV01", 100000);
        repositoryV0.save(member);
    }
}
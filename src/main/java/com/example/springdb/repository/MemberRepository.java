package com.example.springdb.repository;

import com.example.springdb.domain.Member;

import java.sql.SQLException;

public interface MemberRepository {
    Member save(Member member) ;
    Member findById(String memberId) ;
    void update(String memberId, int money);
    void delete(String memberId);
}

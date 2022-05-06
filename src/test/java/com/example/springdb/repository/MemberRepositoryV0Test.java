package com.example.springdb.repository;

import com.example.springdb.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV4", 100000);
        repositoryV0.save(member);

        //findById
        Member findMember = repositoryV0.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(member).isEqualTo(findMember);

        repositoryV0.update(findMember.getMemberId(), 20000);
        Member updateMember = repositoryV0.findById(findMember.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        repositoryV0.delete(member.getMemberId());
        assertThatThrownBy(() -> repositoryV0.findById(findMember.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
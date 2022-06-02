package com.example.springdb.repository;

import com.example.springdb.domain.Member;
import com.example.springdb.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 * * DataSourceUtils.getConnection()
 * * DataSourceUtils.releaseConnection()
 * */

@Slf4j
public class MemberRepositoryV4_2 implements MemberRepository{
    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public MemberRepositoryV4_2(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            int count = pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            DataAccessException ex = exTranslator.translate("save", sql, e);
            throw ex;
        }finally {
            close(conn, pstmt, null);
        }
    }

    public Member findById(String memberId)  {
        String sql = "select * from member where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else{
                throw new NoSuchElementException("member not found member Id = " + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            DataAccessException ex = exTranslator.translate("findById", sql, e);
            throw ex;
        }finally {
            close(conn, pstmt, rs);
        }
    }


    public void update(String memberId, int money)  {
        String sql = "update  member set  money = ? where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            DataAccessException ex = exTranslator.translate("update", sql, e);
            throw ex;
        }finally {
            close(conn, pstmt, rs);
        }
    }

    public void delete(String memberId) {
        String sql = "delete from member where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            DataAccessException ex = exTranslator.translate("delete", sql, e);
            throw ex;
        }finally {
            close(conn, pstmt, rs);
        }
    }

    public Connection getConnection() throws SQLException {
        //트랜 잭션 동기화를 사용하려면 DataSourceUtils를 사용해야한다.
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }

    private void close(Connection conn, Statement stmt, ResultSet rs)  {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        //트랜 잭션 동기화를 사용하려면 DataSourceUtils를 사용해야한다.
        DataSourceUtils.releaseConnection(conn, dataSource);
        //JdbcUtils.closeConnection(conn);
    }
}

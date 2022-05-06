package com.example.springdb.repository;

import com.example.springdb.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

import static com.example.springdb.connection.DBConnectionUtil.getConnection;
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
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
            throw e;
        }finally {
            close(conn, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
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
            throw e;
        }finally {
            close(conn, pstmt, rs);
        }
    }



    private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }

        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("error", e);
            }
        }
    }
}

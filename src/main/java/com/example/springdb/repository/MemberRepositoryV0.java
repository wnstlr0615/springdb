package com.example.springdb.repository;

import com.example.springdb.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

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

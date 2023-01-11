package cnu.swabe.request;

import cnu.swabe.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.sql.*;

import static cnu.swabe.connection.DBConnectionUtil.getConnection;

@Slf4j
@RestController
@RequestMapping("/api")
public class RequestTest {

    @ResponseBody
    @PostMapping("/users/signup")
    public User requestSignup(@RequestBody User user) throws SQLException {
        log.info("post request");
        String sql = "insert into SWABE.PUBLIC.USERS_TB(USER_ID, USER_PW, USER_NM, USER_ISMALE) values(?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPw());
            pstmt.setString(3, user.getNickname());
            pstmt.setBoolean(4, user.isMale());
            pstmt.executeUpdate();
            return user;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }
}

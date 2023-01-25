package cnu.swabe.v0.repository;

import cnu.swabe.v0.domain.User;
import cnu.swabe.v0.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Slf4j
@Repository
public class UserRepository {
    private final JdbcTemplate template;

    public UserRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public int save(UserDTO userDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into USERS_TB(USER_ID, USER_PW, USER_NM, USER_ISMALE) values(?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userDTO.getId());
            preparedStatement.setString(2, userDTO.getPw());
            preparedStatement.setString(3, userDTO.getNickname());
            preparedStatement.setBoolean(4, userDTO.isMale());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("user_no");
    }

    public boolean findByNickName(String nickname) {
        String sql = "select * from USERS_TB where USER_NM = ?";
        User user = template.queryForObject(sql, userExcludedPasswordRowMapper(), nickname);
        if(user == null) {
            return false;
        } else {
            return true;
        }
    }

    public User findById(String id) {
        String sql = "select * from USERS_TB where USER_NM = ?";
        User user = template.queryForObject(sql, userExcludedPasswordRowMapper(), id);
        return user;
    }

    public User findByPw(String Pw) {
        String sql = "select * from USERS_TB where USER_NM = ?";
        User user = template.queryForObject(sql, userExcludedPasswordRowMapper(), Pw);
        return user;
    }

    private RowMapper<User> userExcludedPasswordRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setNo(Integer.parseInt(rs.getString("USER_NO")));
            user.setId(rs.getString("USER_ID"));
            user.setNickname(rs.getString("USER_NM"));
            user.setMale(rs.getBoolean("USER_ISMALE"));
            return user;
        };
    }
}

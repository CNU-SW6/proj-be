package cnu.swabe.v1.repository;

import cnu.swabe.v1.domain.user.User;
import cnu.swabe.v1.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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

    /**
     * version - v1
     * 동적쿼리 MyBatis
     * DTO 필요
     * */
    public User save(UserDTO userDTO) {
        User user = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into USERS_TB(USER_ID, USER_PW, USER_NICKNAME, USER_ISMALE) values(?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userDTO.getId());
            preparedStatement.setString(2, userDTO.getPw());
            preparedStatement.setString(3, userDTO.getNickname());
            preparedStatement.setBoolean(4, userDTO.isMale());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        user = new User((Integer) keyHolder.getKeys().get("USER_NO"), userDTO.getId(), userDTO.getPw(), userDTO.getNickname(), userDTO.isMale());

        return user;
    }

    /**
     * version - v1
     * */
    public User findByNickName(String nickname) {
        User user = null;
        String sql = "select * from USERS_TB where USER_NICKNAME = ?";
        try {
            user = template.queryForObject(sql, userExcludedPasswordRowMapper(), nickname);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return user;
    }

    public User findById(String id) {
        String sql = "select * from USERS_TB where USER_NICKNAME = ?";
        User user = template.queryForObject(sql, userExcludedPasswordRowMapper(), id);
        return user;
    }

    public User findByPw(String Pw) {
        String sql = "select * from USERS_TB where USER_NICKNAME = ?";
        User user = template.queryForObject(sql, userExcludedPasswordRowMapper(), Pw);
        return user;
    }

    private RowMapper<User> userExcludedPasswordRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setNo(Integer.parseInt(rs.getString("USER_NO")));
            user.setId(rs.getString("USER_ID"));
            user.setNickname(rs.getString("USER_NICKNAME"));
            user.setMale(rs.getBoolean("USER_ISMALE"));
            return user;
        };
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setNo(Integer.parseInt(rs.getString("USER_NO")));
            user.setId(rs.getString("USER_ID"));
            user.setId(rs.getString("USER_PW"));
            user.setNickname(rs.getString("USER_NICKNAME"));
            user.setMale(rs.getBoolean("USER_ISMALE"));
            return user;
        };
    }
}

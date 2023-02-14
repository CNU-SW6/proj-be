package cnu.swabe.v2.repository;

import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserSignUpDTO;
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
     * version - v2.1
     * jdbcTemplate
     * */
    public void save(UserEntity user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into USERS_TB(USER_ID, USER_PW, USER_NICKNAME, USER_ISMALE) values(?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getPw());
            preparedStatement.setString(3, user.getNickname());
            preparedStatement.setBoolean(4, user.isMale());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        user.setNo((Integer) keyHolder.getKeys().get("USER_NO"));
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public UserEntity findByNickName(String nickname) {
        UserEntity user = null;
        String sql = "select * from USERS_TB where USER_NICKNAME = ?";
        try {
            user = template.queryForObject(sql, userRowMapper(), nickname);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return user;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public UserEntity findById(String id) {
        UserEntity user = null;
        String sql = "select * from USERS_TB where USER_ID = ?";
        try {
            user = template.queryForObject(sql, userExcludedPasswordRowMapper(), id);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return user;
    }

    public UserEntity findUser(String id, String pw) {
        UserEntity user = null;
        String sql = "select * from USERS_TB where USER_ID = ? AND USER_PW = ?";
        try {
            return template.queryForObject(sql, userRowMapper(), id, pw);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    private RowMapper<UserEntity> userExcludedPasswordRowMapper() {
        return (rs, rowNum) -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setNo(Integer.parseInt(rs.getString("USER_NO")));
            userEntity.setId(rs.getString("USER_ID"));
            userEntity.setNickname(rs.getString("USER_NICKNAME"));
            userEntity.setMale(rs.getBoolean("USER_ISMALE"));
            return userEntity;
        };
    }

    private RowMapper<UserEntity> userRowMapper() {
        return (rs, rowNum) -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setNo(Integer.parseInt(rs.getString("USER_NO")));
            userEntity.setId(rs.getString("USER_ID"));
            userEntity.setPw(rs.getString("USER_PW"));
            userEntity.setNickname(rs.getString("USER_NICKNAME"));
            userEntity.setMale(rs.getBoolean("USER_ISMALE"));
            return userEntity;
        };
    }
}

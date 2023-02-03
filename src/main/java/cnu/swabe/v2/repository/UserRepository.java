package cnu.swabe.v2.repository;

import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserSignUpRequestDTO;
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
     * version - v2
     * jdbcTemplate
     * */
    public UserEntity save(UserSignUpRequestDTO userRequestDTO) {
        UserEntity user = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into USERS_TB(USER_ID, USER_PW, USER_NICKNAME, USER_ISMALE) values(?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userRequestDTO.getId());
            preparedStatement.setString(2, userRequestDTO.getPw());
            preparedStatement.setString(3, userRequestDTO.getNickname());
            preparedStatement.setBoolean(4, userRequestDTO.isMale());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        user = new UserEntity(
                (Integer) keyHolder.getKeys().get("USER_NO"),
                userRequestDTO.getId(),
                userRequestDTO.getPw(),
                userRequestDTO.getNickname(),
                userRequestDTO.isMale()
        );

        return user;
    }

    /**
     * version - v2
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

    public UserEntity findById(String id) {
        String sql = "select * from USERS_TB where USER_ID = ?";
        UserEntity userEntity = template.queryForObject(sql, userExcludedPasswordRowMapper(), id);
        return userEntity;
    }

//    public User findByPw(String Pw) {
//        String sql = "select * from USERS_TB where USER_NICKNAME = ?";
//        User user = template.queryForObject(sql, userExcludedPasswordRowMapper(), Pw);
//        return user;
//    }

    public UserEntity findUser(String id, String pw) {
        String sql = "select * from USERS_TB where USER_ID = ? AND USER_PW = ?";
//        try {
//            return template.queryForObject(sql, userRowMapper(), id, pw);
//        }catch (EmptyResultDataAccessException e){
//            return null;
//        }
        return template.queryForObject(sql, userRowMapper(), id, pw);
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

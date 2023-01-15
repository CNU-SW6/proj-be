package cnu.swabe.v0.repository;

import cnu.swabe.v0.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Slf4j
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
            preparedStatement.setString(4, String.valueOf(userDTO.isMale()));
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().intValue();
    }
}

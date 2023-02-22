package cnu.swabe.v2.repository;

import cnu.swabe.v2.domain.like.LikeEntity;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class LikeRepository {

    private final JdbcTemplate template;

    public LikeRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public void save(LikeEntity like) {
        String sql = "insert into LIKES_TB(USER_LIKE_POST, USER_NO, POST_NO) values (?, ?, ?)";
        StringBuilder sb = new StringBuilder();
        sb.append(like.getUserNo());
        sb.append("_LIKE_");
        sb.append(like.getPostNo());
        String pk = sb.toString();

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pk);
            preparedStatement.setInt(2, like.getUserNo());
            preparedStatement.setInt(3, like.getPostNo());
            return preparedStatement;
        };

        template.update(preparedStatementCreator);
        like.setUserNoLikePostNo(pk);
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public LikeEntity findByPostNoAndUserNo(int postNo, int userNo) {
        LikeEntity like = null;
        String sql = "select * from LIKES_TB where POST_NO and USER_NO = ?";

        try {
            like = template.queryForObject(sql, likeRowMapper(), postNo ,userNo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return like;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public List<LikeEntity> findByUserNo(int userNo) {
        List<LikeEntity> likes = null;
        String sql = "select * from LiKES_TB where USER_NO = ?";

        try {
            likes = template.query(sql, likeRowMapper(), userNo);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return likes;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public void delete(LikeEntity like) {
        String sql = "delete from LIKES_TB where POST_NO = ? AND USER_NO = ? ";
        template.update(sql, like.getPostNo(), like.getUserNo());
    }

    /**
     * version - v2
     * jdbcTemplate
     * */
    public void deleteByPostNo(int postNo) {
        String sql = "delete from LIKES_TB where POST_NO = ?";
        template.update(sql, postNo);
    }

    private RowMapper<LikeEntity> likeRowMapper() {
        return (rs, rowNum) -> {
            LikeEntity like = new LikeEntity(
                    rs.getString("USER_LIKE_POST"),
                    rs.getInt("POST_NO"),
                    rs.getInt("USER_NO")
            );

            return like;
        };
    }
}

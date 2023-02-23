package cnu.swabe.v2.repository;

import cnu.swabe.v2.domain.post.dto.PostUserDetailDTO;
import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.dto.StyleRequestDTO;
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
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class PostRepository {
    private final JdbcTemplate template;

    public PostRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public void save(PostEntity post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into POSTS_TB(DESCRIPTION, IS_SELL, USER_NO, IMAGE_NO, POST_ISMALE) values (?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, post.getDescription());
            preparedStatement.setBoolean(2, post.isSell());
            preparedStatement.setInt(3, post.getUserNo());
            preparedStatement.setInt(4, post.getImageNo());
            preparedStatement.setBoolean(5, post.isMale());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        post.setPostNo((Integer) keyHolder.getKeys().get("POST_NO"));
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * problem. ImageStyleRequestDTO 클래스는 god 클래스가 아닌가
     * problem. 동적 쿼리 시 gender, sort 고려
     */
    public List<PostEntity> findByImageStyle(StyleRequestDTO styleRequestDTO) {
        List<PostEntity> posts = null;
        String sql = "select * from POSTS_TB " +
                "inner join IMAGES_TB " +
                "on POSTS_TB.IMAGE_NO = IMAGES_TB.IMAGE_NO " +
                "where HAT_COLOR=? AND TOP_COLOR=? AND PANTS_COLOR=? AND SHOES_COLOR=?"+
                "order by LIKE_NUM";

        try {
            posts = template.query(sql, postRowMapper(),
                    styleRequestDTO.getHatColor(),
                    styleRequestDTO.getTopColor(),
                    styleRequestDTO.getPantsColor(),
                    styleRequestDTO.getShoesColor()
            );
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return posts;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * problem 1. Join시에는 어떻게 return 해야할까?
     */
    public PostUserDetailDTO findPostAndUserByPostNo(int postNo) {
        PostUserDetailDTO postUserDetailDTO = null;
        String sql = "select POSTS_TB.DESCRIPTION, POSTS_TB.LIKE_NUM, POSTS_TB.IS_SELL, USERS_TB.NICKNAME" +
                "from POSTS_TB " +
                "inner join USERS_TB " +
                "on POSTS_TB.USER_NO = USERS_TB.USER_NO " +
                "where POST_NO = ?";

        try {
            postUserDetailDTO = template.queryForObject(sql, postUserDetailDTORowMapper(), postNo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return postUserDetailDTO;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public PostEntity findByPostNo(int postNo) {
        PostEntity post = null;
        String sql = "select * from POSTS_TB where POST_NO = ?";

        try {
            post = template.queryForObject(sql, postRowMapper(), postNo);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return post;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public List<PostEntity> findByPostNos(List<Integer> postNos) {
        return null;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public List<PostEntity> findByUserNo(int userNo){
        List<PostEntity> postDTOList = null;
        String sql = "select * from POSTS_TB " +
                "where USER_NO = ?" +
                "order by CREATE_AT";

        try {
            postDTOList = template.query(sql, postRowMapper(), userNo);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return postDTOList;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     **/
    public void updateLikeNum(int postNo, int likeNum) {
        String sql = "update POSTS_TB set LIKE_NUM=? where POST_NO=?";
        template.update(sql, likeNum, postNo);
    }

    /**
     * version - v2.1
     * */
    public void deleteByPostNo(int postNo) {
        String sql = "delete from POSTS_TB where POST_NO = ?";
        template.update(sql, postNo);
    }

    private RowMapper<PostEntity> postRowMapper() {
        return (rs, rowNum) -> {
            PostEntity postEntity = new PostEntity();
            postEntity.setPostNo(rs.getInt("POST_NO"));
            postEntity.setUserNo(rs.getInt("USER_NO"));
            postEntity.setImageNo(rs.getInt("IMAGE_NO"));
            postEntity.setDescription(rs.getString("DESCRIPTION"));
            postEntity.setLikeNum(rs.getInt("LIKE_NUM"));
            postEntity.setSell(rs.getBoolean("IS_SELL"));
            postEntity.setMale(rs.getBoolean("POST_ISMALE"));
            postEntity.setCreatedAt(rs.getDate("CREATE_AT").toString());
            return postEntity;
        };
    }

    private RowMapper<PostUserDetailDTO> postUserDetailDTORowMapper() {
        return (rs, rowNum) -> {
            PostUserDetailDTO postUserDetailDTO = new PostUserDetailDTO();
            postUserDetailDTO.setDescription(rs.getString("DESCRIPTION"));
            postUserDetailDTO.setSell(rs.getBoolean("IS_SELL"));
            postUserDetailDTO.setLikeNum(rs.getInt("LIKE_NUM"));
            postUserDetailDTO.setNickname(rs.getString("USER_NICKNAME"));
            return postUserDetailDTO;
        };
    }
}

package cnu.swabe.v2.repository.post;

import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.PostDTO;
import cnu.swabe.v2.domain.image.dto.ImageStyleRequestDTO;
import cnu.swabe.v2.domain.post.dto.PostSaveRequestDTO;
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
import java.util.List;

@Slf4j
@Repository
public class PostRepository {
    private final JdbcTemplate template;

    public PostRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * version - v2
     * jdbcTemplate
     * */
    public PostEntity save(PostSaveRequestDTO postSaveRequestDTO) {
        PostEntity postEntity = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into POSTS_TB(DESCRIPTION, IS_SELL, SELL_URL, USER_NO, IMAGE_NO) values (?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, postSaveRequestDTO.getDescription());
            preparedStatement.setBoolean(2, postSaveRequestDTO.isSell());
            preparedStatement.setString(3, postSaveRequestDTO.getSellUrl());
            preparedStatement.setInt(4, postSaveRequestDTO.getUserNo());
            preparedStatement.setInt(5, postSaveRequestDTO.getImageNo());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        postEntity = new PostEntity(
                (Integer) keyHolder.getKeys().get("post_no"),
                postSaveRequestDTO.getUserNo(),
                postSaveRequestDTO.getImageNo(),
                postSaveRequestDTO.getDescription(),
                postSaveRequestDTO.isSell(),
                0,
                postSaveRequestDTO.getSellUrl()
                );

        return postEntity;
    }

    /**
     * version - v2
     * jdbcTemplate
     */
    public List<PostEntity> findByImageStyle(ImageStyleRequestDTO imageStyleRequestDTO) {
        List<PostEntity> posts = null;
        String sql = "select * from POSTS_TB " +
                "inner join IMAGES_TB on POSTS_TB.IMAGE_NO = IMAGES_TB.IMAGE_NO " +
                "where HAT_COLOR=? AND TOP_COLOR=? AND PANTS_COLOR=? AND SHOES_COLOR=?";

        try {
            posts = template.query(sql, postRowMapper(),
                    imageStyleRequestDTO.getHat(),
                    imageStyleRequestDTO.getTop(),
                    imageStyleRequestDTO.getPants(),
                    imageStyleRequestDTO.getShoes()
            );
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return posts;
    }


    /**
     * version - v1
     * */
    public void deleteByPostNo(int postNo) {
        String sql = "delete from POSTS_TB where POST_NO = ?";
        template.update(sql, postNo);
    }

    /**
     * version - v2
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

    public List<PostDTO> findById(int userNo){
        String sql = "select * from POSTS_TB where USER_NO = ?";
        List<PostDTO> postDTOList = template.query(sql, postDTORowMapper(), userNo);
        return postDTOList;
    }

    private RowMapper<PostDTO> postDTORowMapper() {
        return (rs, rowNum) -> {
            PostDTO postDTO = new PostDTO();
            postDTO.setUserNo(rs.getInt("USER_NO"));
            postDTO.setPostNo(rs.getInt("POST_NO"));
            postDTO.setSell(rs.getBoolean("IS_SELL"));
            return postDTO;
        };
    }

    private RowMapper<PostEntity> postRowMapper() {
        return (rs, rowNum) -> {
            PostEntity postEntity = new PostEntity();
            postEntity.setPostNo(rs.getInt("POST_NO"));
            postEntity.setDescription(rs.getString("DESCRIPTION"));
            postEntity.setSell(rs.getBoolean("IS_SELL"));
            postEntity.setImageNo(rs.getInt("IMAGE_NO"));
            postEntity.setUserNo(rs.getInt("USER_NO"));
            postEntity.setSellUrl(rs.getString("SELL_URL"));
            postEntity.setLikeNum(rs.getInt("LIKE_NUM"));
            return postEntity;
        };
    }
}

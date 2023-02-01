package cnu.swabe.v2.repository.post;

import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.domain.post.dto.PostDTO;
import cnu.swabe.v2.domain.image.dto.ImageStyleDTO;
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
     * version - v1
     * */
    public PostEntity save(PostEntity postEntityDTO) {
        PostEntity postEntity = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into POSTS_TB(DESCRIPTION, IS_SELL, SELL_URL, USER_NO, IMAGE_NO) values (?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, postEntityDTO.getDescription());
            preparedStatement.setBoolean(2, postEntityDTO.isSell());
            preparedStatement.setString(3, postEntityDTO.getSellUrl());
            preparedStatement.setInt(4, postEntityDTO.getUserNo());
            preparedStatement.setInt(5, postEntityDTO.getImageNo());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        postEntity = new PostEntity(
                (Integer) keyHolder.getKeys().get("post_no"),
                postEntityDTO.getUserNo(),
                postEntityDTO.getImageNo(),
                postEntityDTO.getDescription(),
                postEntityDTO.isSell(),
                postEntityDTO.getLikeNum(),
                postEntityDTO.getSellUrl()
                );

        return postEntity;
    }

    /**
     * version - v2
     * jdbcTemplate
     */
    public List<PostEntity> findByImageStyle(ImageStyleDTO styleDTO) {
        List<PostEntity> posts = null;
        String sql = "select * from POSTS_TB " +
                "inner join IMAGES_TB on POSTS_TB.IMAGE_NO = IMAGES_TB.IMAGE_NO " +
                "where HAT_COLOR=? AND TOP_COLOR=? AND PANTS_COLOR=? AND SHOES_COLOR=?";

        try {
            posts = template.query(sql, postRowMapper(),
                    styleDTO.getHat(),
                    styleDTO.getTop(),
                    styleDTO.getPants(),
                    styleDTO.getShoes()
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

    public PostEntity findByPostNo(int postNo) {
        String sql = "select * from POSTS_TB where POST_NO = ?";
        PostEntity postEntity = template.queryForObject(sql, postRowMapper(), postNo);
        return postEntity;
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

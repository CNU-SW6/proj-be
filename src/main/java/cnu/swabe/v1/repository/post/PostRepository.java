package cnu.swabe.v1.repository.post;

import cnu.swabe.v1.domain.Post;
import cnu.swabe.v1.dto.ImageInfoDTO;
import cnu.swabe.v1.dto.PostDTO;
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
     * version - v1
     * */
    public Post save(Post postDTO) {
        Post post = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into POSTS_TB(DESCRIPTION, IS_SELL, SELL_URL, USER_NO, IMAGE_NO) values (?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, postDTO.getDescription());
            preparedStatement.setBoolean(2, postDTO.isSell());
            preparedStatement.setString(3, postDTO.getSellUrl());
            preparedStatement.setInt(4, postDTO.getUserNo());
            preparedStatement.setInt(5, postDTO.getImageNo());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        post = new Post(
                (Integer) keyHolder.getKeys().get("post_no"),
                postDTO.getUserNo(),
                postDTO.getImageNo(),
                postDTO.getDescription(),
                postDTO.isSell(),
                postDTO.getLikeNum(),
                postDTO.getSellUrl()
                );

        return post;
    }

    /**
     * version - v1
     * 동적쿼리 MyBatis
     * */
    public List<PostDTO> findByImageInfo(List<ImageInfoDTO> imageInfoDTO) {
        String sql = "select * from POSTS_TB where IMAGE_NO = ?";
        List<PostDTO> postDTOItems = new ArrayList<>();

        try {
            for (ImageInfoDTO imageInfo : imageInfoDTO) {
                PostDTO postDTO = template.queryForObject(sql, postDTORowMapper(imageInfo.getLocation()), imageInfo.getImageNo());
                postDTOItems.add(postDTO);
            }
        } catch(EmptyResultDataAccessException e) {
            // 이미지는 있는데, 게시물 정보는 없다?
            return null;
        }

        Collections.sort(postDTOItems);
        return postDTOItems;
    }


    /**
     * version - v1
     * */
    public void deleteByPostNo(int postNo) {
        String sql = "delete from POSTS_TB where POST_NO = ?";
        template.update(sql, postNo);
    }

    public Post findByPostNo(int postNo) {
        String sql = "select * from POSTS_TB where POST_NO = ?";
        Post post = template.queryForObject(sql, postRowMapper(), postNo);
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

    private RowMapper<PostDTO> postDTORowMapper(String imageLocation) {
        return (rs, rowNum) -> {
            PostDTO postDTO = new PostDTO();
            postDTO.setPostNo(rs.getInt("POST_NO"));
            postDTO.setSell(rs.getBoolean("IS_SELL"));
            postDTO.setLocation(imageLocation);
            postDTO.setLikeNum(rs.getInt("LIKE_NUM"));
            return postDTO;
        };
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPostNo(rs.getInt("POST_NO"));
            post.setDescription(rs.getString("DESCRIPTION"));
            post.setSell(rs.getBoolean("IS_SELL"));
            post.setImageNo(rs.getInt("IMAGE_NO"));
            post.setUserNo(rs.getInt("USER_NO"));
            post.setSellUrl(rs.getString("SELL_URL"));
            post.setLikeNum(rs.getInt("LIKE_NUM"));
            return post;
        };
    }
}

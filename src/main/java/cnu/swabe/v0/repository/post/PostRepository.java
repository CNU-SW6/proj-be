package cnu.swabe.v0.repository.post;

import cnu.swabe.v0.domain.Post;
import cnu.swabe.v0.dto.ImageInfoDTO;
import cnu.swabe.v0.dto.PostDTO;
import cnu.swabe.v0.dto.StyleDTO;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class PostRepository {
    private final JdbcTemplate template;

    public PostRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public int save(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into POSTS_TB(DESCRIPTION, IS_SELL, SELL_URL, USER_NO, IMAGE_NO) values (?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, post.getDescription());
            preparedStatement.setBoolean(2, post.isSell());
            preparedStatement.setString(3, post.getSellUrl());
            preparedStatement.setInt(4, post.getUserNo());
            preparedStatement.setInt(5, post.getImageNo());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("post_no");
    }

    /**
     * 동적쿼리 필요 .. MyBatis에서 처리
     * */
    public List<PostDTO> findByImageInfo(List<ImageInfoDTO> imageInfoDTO) {
        String sql = "select * from POSTS_TB where IMAGE_NO = ?";
        List<PostDTO> postDTOItems = new ArrayList<>();
        // stream 으로 처리
        for(ImageInfoDTO imageInfo : imageInfoDTO) {
            PostDTO postDTO = template.queryForObject(sql, postDTORowMapper(), imageInfo.getImageNo());
            postDTOItems.add(postDTO);
        }

        return postDTOItems;
    }

    public void deleteByPostNo(int postNo) {
        String sql = "delete from POSTS_TB where POST_NO = ?";
        template.update(sql, postNo);
        return;
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

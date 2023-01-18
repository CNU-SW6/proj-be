package cnu.swabe.v0.repository;

import cnu.swabe.v0.domain.Post;
import cnu.swabe.v0.dto.ImageInfoDTO;
import cnu.swabe.v0.dto.StyleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class PostRepository {
    private final JdbcTemplate template;

    public PostRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    /**
     * 동적쿼리 필요 .. MyBatis에서 처리
     * */
    public List<Post> findByImageInfo(List<ImageInfoDTO> imageInfoDTO) {
        String sql = "select * from POSTS_TB where IMAGE_NO = ?";
        List<Post> postItems = new ArrayList<>();
        // stream 으로 처리
        for(ImageInfoDTO imageInfo : imageInfoDTO) {
            Post post = template.queryForObject(sql, postRowMapper(), imageInfo.getImageNo());
            postItems.add(post);
        }

        return postItems;
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

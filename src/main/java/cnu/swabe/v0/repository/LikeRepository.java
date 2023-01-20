package cnu.swabe.v0.repository;

import cnu.swabe.v0.domain.Like;
import cnu.swabe.v0.dto.LikeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

@Slf4j
@Repository
public class LikeRepository {

    private final JdbcTemplate template;

    public LikeRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public Like save(LikeDTO likeDTO) {
        StringBuilder sb = new StringBuilder();
        String sql = "insert into LIKES_TB(USER_LIKE_POST, USER_NO, POST_NO) values (?, ?, ?)";
        sb.append(likeDTO.getUserNo());
        sb.append("_LIKE_");
        sb.append(likeDTO.getPostNo());
        String pk = sb.toString();
        template.update(sql, pk, likeDTO.getUserNo(), likeDTO.getPostNo());
        Like like = new Like(pk, likeDTO.getPostNo(), likeDTO.getUserNo());
        return like;
    }
}

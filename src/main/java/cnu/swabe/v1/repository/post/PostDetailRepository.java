package cnu.swabe.v1.repository.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Slf4j
@Repository
public class PostDetailRepository {
    private final JdbcTemplate template;

    public PostDetailRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * DTO가 필요한 이유...?
     * 여기서 +1 된 값을 return 할 필요가 있을까?
     * */
    public int updateLikeNumByPostNo(int postNo, int likeNum) {
        String sql = "update POSTS_TB set LIKE_NUM=? where POST_NO=?";
        template.update(sql, likeNum+1, postNo);
        return likeNum+1;
    }
}

package cnu.swabe.v1.repository;

import cnu.swabe.v1.domain.like.dto.LikeBusinessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class LikeRepository {

    private final JdbcTemplate template;

    public LikeRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public String save(LikeBusinessDTO likeBusinessDTO) {
        StringBuilder sb = new StringBuilder();
        String sql = "insert into LIKES_TB(USER_LIKE_POST, USER_NO, POST_NO) values (?, ?, ?)";
        sb.append(likeBusinessDTO.getUserNo());
        sb.append("_LIKE_");
        sb.append(likeBusinessDTO.getPostNo());
        String pk = sb.toString();
        template.update(sql, pk, likeBusinessDTO.getUserNo(), likeBusinessDTO.getPostNo());
        return pk;
    }

    public List<LikeBusinessDTO> findLikePost(int userNo) {
        String sql = "select * from LIKES_TB where USER_NO = ?";
        return template.query(sql, LikeBusinessDTORowMapper(), userNo);
    }

    private RowMapper<LikeBusinessDTO> LikeBusinessDTORowMapper() {
        return (rs, rowNum) -> {
            LikeBusinessDTO likeBusinessDTO = new LikeBusinessDTO();
            likeBusinessDTO.setPostNo(rs.getInt("POST_NO"));
            likeBusinessDTO.setUserNo(rs.getInt("USER_NO"));
            return likeBusinessDTO;
        };
    }

    public void delete(LikeBusinessDTO likeBusinessDTO) {
        String sql = "delete from LIKES_TB where USER_NO = ? AND POST_NO = ?";
        template.update(sql, likeBusinessDTO.getUserNo(), likeBusinessDTO.getPostNo());
    }


    /**
     * version - v1
     * */
    public void deleteByPostNo(int postNo) {
        String sql = "delete from LIKES_TB where POST_NO = ?";
        template.update(sql, postNo);
    }
}

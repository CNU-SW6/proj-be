package cnu.swabe.v2.repository;

import cnu.swabe.v2.dto.PostAndUserDetailDTO;
import cnu.swabe.v2.domain.post.PostEntity;
import cnu.swabe.v2.dto.PostSearchListResponseDTO;
import cnu.swabe.v2.dto.StyleSearchRequestDTO;
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
     */
    public List<PostSearchListResponseDTO> findByImageStyle(StyleSearchRequestDTO styleSearchRequestDTO) {
        List<PostSearchListResponseDTO> posts = null;
        StringBuilder sb = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sb.append("select *, IMAGES_TB.LOCATION from POSTS_TB ");
        sb.append("inner join IMAGES_TB on POSTS_TB.IMAGE_NO = IMAGES_TB.IMAGE_NO ");
        sb.append("where ");
        if(styleSearchRequestDTO.getHatColor() != null) {
            sb.append("HAT_COLOR=? ");
            params.add(styleSearchRequestDTO.getHatColor());
            sb.append("and ");
        }

        if(styleSearchRequestDTO.getTopColor() != null) {
            sb.append("TOP_COLOR=? ");
            params.add(styleSearchRequestDTO.getTopColor());
            sb.append("and ");
        }

        if(styleSearchRequestDTO.getPantsColor() != null) {
            sb.append("PANTS_COLOR=? ");
            params.add(styleSearchRequestDTO.getPantsColor());
            sb.append("and ");
        }

        if(styleSearchRequestDTO.getShoesColor() != null) {
            sb.append("SHOES_COLOR=? ");
            params.add(styleSearchRequestDTO.getShoesColor());
            sb.append("and ");
        }

        if(styleSearchRequestDTO.getGender().equals("male")) sb.append("POST_ISMALE=true ");
        if(styleSearchRequestDTO.getGender().equals("female")) sb.append("POST_ISMALE=false ");
        sb.append("order by ");
        if(styleSearchRequestDTO.getSort().equals("like")) sb.append("LIKE_NUM DESC");
        if(styleSearchRequestDTO.getSort().equals("newest")) sb.append("CREATED_AT DESC");
        String sql = sb.toString();

        try {
            posts = template.query(sql, postSearchListResponseDTOMapper(), params.toArray());
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
    public PostAndUserDetailDTO findPostAndUserByPostNo(int postNo) {
        PostAndUserDetailDTO postAndUserDetailDTO = null;
        String sql = "select POSTS_TB.DESCRIPTION, POSTS_TB.LIKE_NUM, POSTS_TB.IS_SELL, USERS_TB.USER_NICKNAME " +
                "from POSTS_TB " +
                "inner join USERS_TB " +
                "on POSTS_TB.USER_NO = USERS_TB.USER_NO " +
                "where POST_NO = ?";

        try {
            postAndUserDetailDTO = template.queryForObject(sql, postAndUserDetailDTORowMapper(), postNo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return postAndUserDetailDTO;
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
    public List<PostSearchListResponseDTO> findByPostNos(List<Integer> postNos) {
        List<PostSearchListResponseDTO> postSearchListResponseDTO = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select POSTS_TB.POST_NO, POSTS_TB.IS_SELL, IMAGES_TB.LOCATION, POSTS_TB.LIKE_NUM ");
        sb.append("from POSTS_TB ");
        sb.append("inner join IMAGES_TB ");
        sb.append("on POSTS_TB.IMAGE_NO = IMAGES_TB.IMAGE_NO ");
        if(postNos.size() != 0) {
            sb.append("where POSTS_TB.POST_NO IN (");
            for (int i = 0; i < postNos.size(); i++) {
                if (i != postNos.size() - 1) {
                    sb.append("?, ");
                } else {
                    sb.append("?");
                }
            }
        }
        sb.append(")");
        String sql = sb.toString();

        try {
            postSearchListResponseDTO = template.query(sql, postSearchListResponseDTOMapper(), postNos.toArray());
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return postSearchListResponseDTO;
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * */
    public List<PostSearchListResponseDTO> findByUserNo(int userNo){
        List<PostSearchListResponseDTO> postSearchListResponseDTO = null;
        String sql = "select POSTS_TB.POST_NO, POSTS_TB.IS_SELL, IMAGES_TB.LOCATION, POSTS_TB.LIKE_NUM " +
                "from POSTS_TB " +
                "inner join IMAGES_TB " +
                "on POSTS_TB.IMAGE_NO = IMAGES_TB.IMAGE_NO " +
                "where POSTS_TB.USER_NO = ?";

        try {
            postSearchListResponseDTO = template.query(sql, postSearchListResponseDTOMapper(), userNo);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }

        return postSearchListResponseDTO;
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
            postEntity.setCreatedAt(rs.getDate("CREATED_AT").toString());
            return postEntity;
        };
    }

    private RowMapper<PostSearchListResponseDTO> postSearchListResponseDTOMapper() {
        return (rs, rowNum) -> {
            PostSearchListResponseDTO postSearchListResponseDTO = new PostSearchListResponseDTO(
                    rs.getInt("POST_NO"),
                    rs.getBoolean("IS_SELL"),
                    rs.getString("LOCATION"),
                    rs.getInt("LIKE_NUM")
            );
            return postSearchListResponseDTO;
        };
    }

    private RowMapper<PostAndUserDetailDTO> postAndUserDetailDTORowMapper() {
        return (rs, rowNum) -> {
            PostAndUserDetailDTO postAndUserDetailDTO = new PostAndUserDetailDTO();
            postAndUserDetailDTO.setDescription(rs.getString("DESCRIPTION"));
            postAndUserDetailDTO.setSell(rs.getBoolean("IS_SELL"));
            postAndUserDetailDTO.setLikeNum(rs.getInt("LIKE_NUM"));
            postAndUserDetailDTO.setNickname(rs.getString("USER_NICKNAME"));
            return postAndUserDetailDTO;
        };
    }
}

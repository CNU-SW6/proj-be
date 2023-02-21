package cnu.swabe.v2.repository;

import cnu.swabe.v2.dto.ImageInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import cnu.swabe.v2.domain.image.ImageEntity;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Repository
public class ImageRepository {
    private final JdbcTemplate template;

    public ImageRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * version - v2.1
     * jdbcTemplate
     * problem1. 모든 옵션 다 선택 후 저장 가정
     * problem2. 객체 생성 or 파라미터에 바인딩
     * */
    public void save(ImageEntity image) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into IMAGES_TB(USER_NO, LOCATION, HAT_COLOR, TOP_COLOR, PANTS_COLOR, SHOES_COLOR) " +
                "values (?, ?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, image.getUserNo());
            preparedStatement.setString(2, image.getLocation());
            preparedStatement.setString(3, image.getHatColor());
            preparedStatement.setString(4, image.getTopColor());
            preparedStatement.setString(5, image.getPantsColor());
            preparedStatement.setString(6, image.getShoesColor());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        image.setNo((Integer) keyHolder.getKeys().get("IMAGE_NO"));
    }

    public void deleteByImageNo(int imageNo) {
        String sql = "delete from IMAGES_TB where IMAGE_NO = ?";
        template.update(sql, imageNo);
    }
    

    private RowMapper<ImageInfoDTO> imageInfoDTORowMapper() {
        return (rs, rowNum) -> {
            ImageInfoDTO imageInfoDTO = new ImageInfoDTO();
            imageInfoDTO.setImageNo(rs.getInt("IMAGE_NO"));
            imageInfoDTO.setLocation(rs.getString("LOCATION"));
            return imageInfoDTO;
        };
    }

}

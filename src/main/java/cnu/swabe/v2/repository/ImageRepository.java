package cnu.swabe.v2.repository;

import cnu.swabe.v2.domain.image.Image;
import cnu.swabe.v2.dto.ImageInfoDTO;
import cnu.swabe.v2.dto.StyleDTO;
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
public class ImageRepository {
    private final JdbcTemplate template;

    public ImageRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * version - v1
     * 동적쿼리 MyBatis
     * 모든 옵션 다 선택 후 저장 가정
     * */
    public Image save(Image imageDTO) {
        Image image = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into IMAGES_TB(USER_NO, LOCATION, HAT_COLOR, TOP_COLOR, PANTS_COLOR, SHOES_COLOR) values (?, ?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, imageDTO.getUserNo());
            preparedStatement.setString(2, imageDTO.getLocation());
            preparedStatement.setString(3, imageDTO.getHatColor());
            preparedStatement.setString(4, imageDTO.getTopColor());
            preparedStatement.setString(5, imageDTO.getPantsColor());
            preparedStatement.setString(6, imageDTO.getShoesColor());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        image = new Image(
                (Integer) keyHolder.getKeys().get("IMAGE_NO"),
                imageDTO.getUserNo(),
                imageDTO.getLocation(),
                imageDTO.getHatColor(),
                imageDTO.getTopColor(),
                imageDTO.getPantsColor(),
                imageDTO.getShoesColor()
        );
        return image;
    }

    /**
     * version - v1
     * 동적쿼리 MyBatis
     * DTO 분리
    * */
    public List<ImageInfoDTO> findByStyle(StyleDTO styleDTO) {
        List<ImageInfoDTO> imageInfoDTO = null;
        String sql = "select IMAGE_NO, LOCATION from IMAGES_TB where HAT_COLOR=? AND TOP_COLOR=? AND PANTS_COLOR=? AND SHOES_COLOR=?";
        try {
            imageInfoDTO = template.query(sql, imageInfoDTORowMapper(),
                    styleDTO.getHat(),
                    styleDTO.getTop(),
                    styleDTO.getPants(),
                    styleDTO.getShoes()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return imageInfoDTO;
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

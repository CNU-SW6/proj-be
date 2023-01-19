package cnu.swabe.v0.repository;

import cnu.swabe.v0.dto.ImageInfoDTO;
import cnu.swabe.v0.dto.StyleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class ImageRepository {
    private final JdbcTemplate template;

    public ImageRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * 동적쿼리 필요
     * MyBatis 사용 이전(jdbcTemplate)에서는
     * 모든 옵션 다 선택 후 탐색 가정
    * */
    public List<ImageInfoDTO> findByStyle(StyleDTO styleDTO) {
        String sql = "select IMAGE_NO, LOCATION from IMAGES_TB where HAT_COLOR=? AND TOP_COLOR=? AND PANTS_COLOR=? AND SHOES_COLOR=?";
        List<ImageInfoDTO> imageInfoDTO = template.query(sql, imageInfoDTORowMapper(),
                styleDTO.getHat(),
                styleDTO.getTop(),
                styleDTO.getPants(),
                styleDTO.getShoes()
        );

        return imageInfoDTO;
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

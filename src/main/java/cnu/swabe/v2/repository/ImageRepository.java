package cnu.swabe.v2.repository;

import cnu.swabe.v2.domain.image.ImageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Slf4j
@Repository
public class ImageRepository {
    private final JdbcTemplate template;

    public ImageRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * version - v2
     * jdbcTemplate
     * problem1. 모든 옵션 다 선택 후 저장 가정
     * problem2. 객체 생성 or 파라미터에 바인딩
     * */
    public ImageEntity save(ImageEntity imageEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into IMAGES_TB(USER_NO, LOCATION, HAT_COLOR, TOP_COLOR, PANTS_COLOR, SHOES_COLOR) " +
                "values (?, ?, ?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, imageEntity.getUserNo());
            preparedStatement.setString(2, imageEntity.getLocation());
            preparedStatement.setString(3, imageEntity.getHatColor());
            preparedStatement.setString(4, imageEntity.getTopColor());
            preparedStatement.setString(5, imageEntity.getPantsColor());
            preparedStatement.setString(6, imageEntity.getShoesColor());
            return preparedStatement;
        };

        template.update(preparedStatementCreator, keyHolder);
        ImageEntity image = new ImageEntity(
                (Integer) keyHolder.getKeys().get("IMAGE_NO"),
                imageEntity.getUserNo(),
                imageEntity.getLocation(),
                imageEntity.getHatColor(),
                imageEntity.getTopColor(),
                imageEntity.getPantsColor(),
                imageEntity.getShoesColor()
        );

        return image;
    }

    public void deleteByImageNo(int imageNo) {
        String sql = "delete from IMAGES_TB where IMAGE_NO = ?";
        template.update(sql, imageNo);
    }
}

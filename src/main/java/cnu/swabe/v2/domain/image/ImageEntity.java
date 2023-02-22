package cnu.swabe.v2.domain.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    private int imageNo;
    private int userNo;
    private String fileName;
    private String location;
    private String hatColor;
    private String topColor;
    private String pantsColor;
    private String shoesColor;

    public ImageEntity(int userNo, String fileName, String location, String hatColor, String topColor, String pantsColor, String shoesColor) {
        this.userNo = userNo;
        this.fileName = fileName;
        this.location = location;
        this.hatColor = hatColor;
        this.topColor = topColor;
        this.pantsColor = pantsColor;
        this.shoesColor = shoesColor;
    }
}

package yapily.marvel.explorer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Image {

    /**
     * The directory path of to the image.
     */
    private String path;

    /**
     * The file extension for the image.
     */
    private String extension;

    public Image() {
    }
}

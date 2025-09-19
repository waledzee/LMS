package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private String edition;
    private Integer publicationYear;
    private String summary;
    private String coverImage;
    private Long publisherId;
    private Long languageId;
    private List<Long> authorIds;
    private List<Long> categoryIds;
}

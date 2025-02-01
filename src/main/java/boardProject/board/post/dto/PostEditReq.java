package boardProject.board.post.dto;

import lombok.Data;

@Data
public class PostEditReq {
    private String title;
    private String body;
    private Boolean isPublic;

}

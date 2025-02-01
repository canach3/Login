package boardProject.board.post.dto;

import lombok.Data;

@Data
public class PostSaveReq {
    private String title;
    private String body;
    private boolean isPublic;
}

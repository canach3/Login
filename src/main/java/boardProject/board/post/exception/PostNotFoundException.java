package boardProject.board.post.exception;

import boardProject.board.common.code.PostErrorCode;

public class PostNotFoundException extends PostException {
    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}

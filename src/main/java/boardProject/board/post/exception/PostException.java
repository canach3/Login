package boardProject.board.post.exception;

import boardProject.board.common.ApplicationException;
import boardProject.board.common.code.ResponseCode;

public class PostException extends ApplicationException {
    public PostException(ResponseCode errorCode) {
        super(errorCode);
    }
}

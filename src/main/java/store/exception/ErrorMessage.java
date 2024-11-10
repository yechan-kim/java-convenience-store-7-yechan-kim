package store.exception;

public enum ErrorMessage {

    FILE_LOAD_ERROR("파일을 불러오는데 실패했습니다."),
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

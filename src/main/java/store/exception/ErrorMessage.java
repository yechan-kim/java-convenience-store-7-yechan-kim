package store.exception;

public enum ErrorMessage {

    FILE_LOAD_ERROR("파일을 불러오는데 실패했습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

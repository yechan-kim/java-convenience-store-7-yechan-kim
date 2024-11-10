package store.controller;

public enum Command {
    YES("Y"),
    NO("N");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

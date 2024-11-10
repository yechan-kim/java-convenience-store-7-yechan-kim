package store.view;

public enum ReceiptFormat {
    FINAL_AMOUNT("내실돈\t\t\t\t\t\t%,10d\n"),
    FOOTER("======================================="),
    GIFT_FORMAT("%-10s\t\t%,-4d\n"),
    GIFT_HEADER("===============증\t정================="),
    HEADER("==============W 편의점================"),
    MEMBERSHIP_DISCOUNT("멤버십할인\t\t\t\t\t%10s\n"),
    PRODUCT_FORMAT("%-10s\t\t%,-4d\t%,10d\n"),
    PRODUCT_HEADER("상품명\t\t\t\t수량\t\t금액"),
    PROMOTION_DISCOUNT("행사할인\t\t\t\t\t%10s\n"),
    TOTAL_AMOUNT("총구매액\t\t\t%-4d\t%,10d\n");

    private final String format;

    ReceiptFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}

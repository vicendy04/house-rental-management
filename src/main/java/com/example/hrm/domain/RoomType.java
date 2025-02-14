package com.example.hrm.domain;

/**
 * <a href="https://tokyoportfolio.com/1r-1k-1dk-1ldk-apartments-japan/">...</a>
 */
public enum RoomType {
    ONE_ROOM("1R"),   // Phòng đơn có bếp trong cùng một không gian
    ONE_K("1K"),      // Phòng có bếp tách biệt
    ONE_DK("1DK"),    // Phòng có khu vực bếp và ăn uống tách riêng
    ONE_LDK("1LDK"),  // Có phòng khách, bếp và phòng ngủ riêng biệt
    TWO_LDK("2LDK"),  // Giống 1LDK nhưng có thêm phòng ngủ
    THREE_LDK("3LDK");// Giống 2LDK nhưng có thêm một phòng ngủ

    private final String label;

    RoomType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}

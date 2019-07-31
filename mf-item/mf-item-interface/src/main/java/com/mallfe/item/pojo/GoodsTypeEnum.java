package com.mallfe.item.pojo;

public enum GoodsTypeEnum {
    正品(0), 残次(1), 样机(2), 赠品(3);
    private int value;

    GoodsTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

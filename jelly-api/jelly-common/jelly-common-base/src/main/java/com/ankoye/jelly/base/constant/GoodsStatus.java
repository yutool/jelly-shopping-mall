package com.ankoye.jelly.base.constant;


public class GoodsStatus {
    public static final Integer SUCCESS = 0;    // 审核成功
    public static final Integer AUDIT = 1;      // 审核中
    public static final Integer FAILED = 2;     // 审核失败

//    SUCCESS(0, "审核成功"),
//    AUDIT(1, "审核中"),
//    FAILED(2, "审核失败");
//
//    private Integer code;
//    private String hint;
//
//    GoodsStatus(Integer code, String hint) {
//        this.code = code;
//        this.hint = hint;
//    }
//
//    public static Integer getCode(String name) {
//        for (GoodsStatus item : GoodsStatus.values()) {
//            if (item.name().equals(name)) {
//                return item.code;
//            }
//        }
//        return null;
//    }
//
//    public static String getHint(String name) {
//        for (GoodsStatus item : GoodsStatus.values()) {
//            if (item.name().equals(name)) {
//                return item.hint;
//            }
//        }
//        return name;
//    }
}

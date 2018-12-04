package com.lx.activity.services.processor.constants;

public class ActivityAPIConstants {

    /**
     * 是否允许抽奖Code
     */
    public enum IsAllowDrawCode {
        /**
         * 没有抽奖资格
         */
        LUCK_DRAW_NOT_ALLOW(-1),
        /**
         * 不在活动时间
         */
        LUCK_DRAW_NOT_TIME_ALLOW(-2),
        /**
         * 不允许不登录抽奖
         */
        LUCK_DRAW_NOT_SIGN_ALLOW(-3),
        /**
         * 已经抽完奖
         */
        LUCK_DRAW_HAS_DRAW(0),
        /**
         * 不知名的错误
         */
        LUCK_DRAW_NOT_KNOW_DRAW(-4),
        /**
         * 奖品领完了
         */
        LUCK_DRAW_NULL_DRAW(-5);

        private int value;

        private IsAllowDrawCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}

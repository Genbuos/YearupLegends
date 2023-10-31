package utils;

public class Constants {

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;

        public static final int JUMP = 3;
        public static final int FALLING = 6;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 2;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case RUNNING:
                case IDLE:
                    return 8;
                case HIT:
                    return 3;
                case JUMP:
                    return 2;
                case ATTACK_1:
                    return 5;
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}

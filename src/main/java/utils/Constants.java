package utils;

public class Constants {

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants{
        public static final int IDLE = 5;
        public static final int RUNNING = 0;

        public static final int JUMP = 7;
        public static final int ROLLING = 2;
        public static final int DEATH = 1;

        public static final int ATTACK_1 = 3;
        public static final int WALL_SLIDE = 4;
        public static final int ATTACK_JUMP_2 = 6;

        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case RUNNING:
                case DEATH:
                case ROLLING:
                case ATTACK_1:
                    return 7;
                case IDLE:
                case ATTACK_JUMP_2:
                    return 4;


                case WALL_SLIDE:
                    return 6;

                default:
                    return 1;
            }
        }
    }
}

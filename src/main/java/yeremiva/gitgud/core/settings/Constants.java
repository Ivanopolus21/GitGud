package yeremiva.gitgud.core.settings;

import yeremiva.gitgud.controller.GameController;

import static yeremiva.gitgud.core.settings.EnemyType.SKELETON;

/**
 * Constants.
 * <p>
 *     Class that keeps the constants that can be accessed in every other class.
 * </p>
 */
public class Constants {
    public static final float GRAVITY = 0.04f * GameController.SCALE;
    public static final int ANI_SPEED = 25;

    /**
     * Object Constants.
     * <p>
     *     Class that keeps the object constants that can be accessed in the class.
     * </p>
     */
    public static class ObjectConstants {
        public static final int RED_GEM = 0;
        public static final int BLUE_GEM = 1;
        public static final int BLUE_GEMSTONE = 2;
        public static final int RED_GAMESTONE = 3;
        public static final int SPIKE = 4;

        public static final int RED_GEM_VALUE = 15;
        public static final int BLUE_GEM_VALUE = 10;

        public static final int GEM_WIDTH_DEFAULT = 12;
        public static final int GEM_HEIGHT_DEFAULT = 16;
        public static final int GEM_WIDTH = (int) (GEM_WIDTH_DEFAULT * GameController.SCALE);
        public static final int GEM_HEIGHT = (int) (GEM_HEIGHT_DEFAULT * GameController.SCALE);

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (CONTAINER_WIDTH_DEFAULT * GameController.SCALE);
        public static final int CONTAINER_HEIGHT = (int) (CONTAINER_HEIGHT_DEFAULT * GameController.SCALE);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (SPIKE_WIDTH_DEFAULT * GameController.SCALE);
        public static final int SPIKE_HEIGHT = (int) (SPIKE_HEIGHT_DEFAULT * GameController.SCALE);

        /**
         * Get Sprite Amout.
         * <p>
         *     Method that return the amount of gem and gemstone sprites for the animations.
         * </p>
         *
         * @param object_type the object type
         * @return the number of sprites
         */
        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case RED_GEM:
                case BLUE_GEM:
                    return 7;
                case BLUE_GEMSTONE:
                case RED_GAMESTONE:
                    return 8;
            }
            return 1;
        }
    }

    /**
     * Enemy Constants.
     * <p>
     *     Class that keeps the enemy constants that can be accessed in the class.
     * </p>
     */
    public static class EnemyConstants {
        public static final int SKELETON = 0;
        // udelat ENUM SKELETON et.ordinal()
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int SKELETON_WIDTH_DEFAULT = 64;
        public static final int SKELETON_HEIGHT_DEFAULT = 64;

        public static final int SKELETON_WIDTH = (int) (SKELETON_WIDTH_DEFAULT *  GameController.SCALE);
        public static final int SKELETON_HEIGHT = (int) (SKELETON_HEIGHT_DEFAULT *  GameController.SCALE);

        public static final int SKELETON_DRAWOFFSET_X = (int) (24 * GameController.SCALE);
        public static final int SKELETON_DRAWOFFSET_Y = (int) (47 * GameController.SCALE);

        /**
         * Get Sprite Amount.
         * <p>
         *     Method that return the amount of enemy sprites for the animations.
         * </p>
         *
         * @param enemy_type the enemy type
         * @param enemy_state the enemy state
         * @return the number of sprites
         */
        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case SKELETON:
                    switch(enemy_state) {
                        case IDLE:
                        case HIT:
                            return 4;
                        case RUNNING:
                        case DEAD:
                            return 6;
                        case ATTACK:
                            return 5;
                    }
            }
            return 0;
        }
    }

    /**
     * Environment Constants.
     * <p>
     *     Class that keeps the environment (background) constants that can be accessed in the class.
     * </p>
     */
    public static class Environment{
        public static final int BIG_BG_OBJECT_WIDTH_DEFAULT = 448;
        public static final int BIG_BG_OBJECT_HEIGHT_DEFAULT = 101;
        public static final int SMALL_BG_OBJECT_WIDTH_DEFAULT = 74;
        public static final int SMALL_BG_OBJECT_HEIGHT_DEFAULT = 24;

        public static final int BIG_BG_OBJECT_WIDTH = (int) (BIG_BG_OBJECT_WIDTH_DEFAULT * GameController.SCALE);
        public static final int BIG_BG_OBJECT_HEIGHT = (int) (BIG_BG_OBJECT_HEIGHT_DEFAULT * GameController.SCALE);
        public static final int SMALL_BG_OBJECT_WIDTH = (int) (SMALL_BG_OBJECT_WIDTH_DEFAULT * GameController.SCALE);
        public static final int SMALl_BG_OBJECT_HEIGHT = (int) (SMALL_BG_OBJECT_HEIGHT_DEFAULT * GameController.SCALE);
    }

    /**
     * View Constants.
     * <p>
     *     Class that keeps the view constants that can be accessed in the view classes.
     * </p>
     */
    public static class View{

        /**
         * Buttons Constants.
         * <p>
         *     Class that keeps the view buttons constants that can be accessed in the view classes.
         * </p>
         */
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * GameController.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * GameController.SCALE);
        }

        /**
         * Pause Buttons Constants.
         * <p>
         *     Class that keeps the pause buttons constants that can be accessed in the class.
         * </p>
         */
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * GameController.SCALE);
        }

        /**
         * URM Buttons Constants.
         * <p>
         *     Class that keeps the URM (UNPAUSE, REPLAY, MENU) buttons constants that can be accessed in the class.
         * </p>
         */
        public static class URMButtons{
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * GameController.SCALE);
        }

        /**
         * Volume Buttons Constants.
         * <p>
         *     Class that keeps the volume buttons constants that can be accessed in the class.
         * </p>
         */
        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;
            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * GameController.SCALE);
            public static final int VOLUME_HEIGHT =  (int) (VOLUME_DEFAULT_HEIGHT * GameController.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * GameController.SCALE);
        }
    }

    /**
     * Directions Constants.
     * <p>
     *     Class that keeps the direction constants for the characters that can be accessed in the classes.
     * </p>
     */
    public static class Directions{
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }

    /**
     * Player Constants.
     * <p>
     *     Class that keeps the player constants that can be accessed in the player class.
     * </p>
     */
    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACKING = 2;
        public static final int JUMPING = 3;
        public static final int FALLING = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        /**
         * Get Sprite Amount.
         * <p>
         *     Method that return the amount of player sprites for the animations.
         * </p>
         *
         * @param player_action the player action
         * @return the number of sprites
         */
        public static int GetSpriteAmount(int player_action){
            switch(player_action){
                case IDLE:
                    return 2;
                case RUNNING:
                case ATTACKING:
                case DEAD:
                    return 8;
                case JUMPING:
                case HIT:
                    return 4;
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}

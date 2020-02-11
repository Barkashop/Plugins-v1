package gamer.bmagic.spell;


public class SpellUtils {



    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((min - max) + 1));
    }

}

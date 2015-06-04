package utils;

import java.util.Random;

/**
 * Created by Ewa on 04/06/2015.
 */
public class Generator {

    public static int generateRandomInt(int min, int max) {
        Random random = new Random();
        return  random.nextInt(max - min) + min;
    }
}

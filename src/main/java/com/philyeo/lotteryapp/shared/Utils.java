package com.philyeo.lotteryapp.shared;

public class Utils {

    public static String getChineseZodiac(int year) {
        String[] zodiacSigns = {"Monkey", "Rooster", "Dog", "Pig", "Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat"};
//        int startYear = 1900; // Starting year of the Chinese Zodiac cycle

        return zodiacSigns[year % 12];
    }

}

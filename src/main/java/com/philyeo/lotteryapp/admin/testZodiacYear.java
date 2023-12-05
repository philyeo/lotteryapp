package com.philyeo.lotteryapp.admin;

import java.util.Scanner;

public class testZodiacYear {

    public static void main(String[] args) {
        int bYear;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your Birth Year : ");
        bYear = input.nextInt();
        // Variable defined, created Scanner object and injected to defined variable.

        switch (bYear % 12) {
            case 0 -> System.out.println("Your Chinese Zodiac is Monkey.");
            case 1 -> System.out.println("Your Chinese Zodiac is Rooster.");
            case 2 -> System.out.println("Your Chinese Zodiac is Dog.");
            case 3 -> System.out.println("Your Chinese Zodiac is Pig.");
            case 4 -> System.out.println("Your Chinese Zodiac is Rat.");
            case 5 -> System.out.println("Your Chinese Zodiac is Ox.");
            case 6 -> System.out.println("Your Chinese Zodiac is Tiger.");
            case 7 -> System.out.println("Your Chinese Zodiac is Rabbit.");
            case 8 -> System.out.println("Your Chinese Zodiac is Dragon.");
            case 9 -> System.out.println("Your Chinese Zodiac is Snake.");
            case 10 -> System.out.println("Your Chinese Zodiac is Horse.");
            case 11 -> System.out.println("Your Chinese Zodiac is Goat.");
        }
        // Chinese zodiacs were calculated according to the remainder of the year of birth divided by 12.
        // Printed on the screen with a switch case.
    }

}

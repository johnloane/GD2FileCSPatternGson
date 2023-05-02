package com.dkit.gd2.johnloane.core;

import java.util.Scanner;

public class Utility {
    public static String getFilePathInput(Scanner keyboard)
    {
        System.out.println("Enter the file path: ");
        String filePath = keyboard.nextLine();
        if (filePath.isBlank())
        {
            System.out.println("File path cannot be empty");
            getFilePathInput(keyboard);
        }
        return filePath;
    }
}

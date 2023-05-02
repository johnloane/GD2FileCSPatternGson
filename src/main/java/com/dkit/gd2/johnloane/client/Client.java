package com.dkit.gd2.johnloane.client;

import com.dkit.gd2.johnloane.command.Command;
import com.dkit.gd2.johnloane.command.CommandFactory;
import com.dkit.gd2.johnloane.core.ServiceDetails;
import com.dkit.gd2.johnloane.core.FileServiceProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", ServiceDetails.SERVER_PORT)){

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());

            Scanner keyboard = new Scanner(System.in);
            int choice = -1;
            FileServiceProtocol command;

            while (choice != 0){
                printMenu();
                choice = getIntInput(keyboard);

                switch (choice) {
                    case 0 -> command = FileServiceProtocol.QUIT;
                    case 1 -> command = FileServiceProtocol.EXISTS;
                    case 2 -> command = FileServiceProtocol.LASTEDIT;
                    case 3 -> command = FileServiceProtocol.FIRSTFIVELINES;
                    default -> {
                        System.out.println("Invalid choice");
                        continue;
                    }
                }

                CommandFactory commandFactory = new CommandFactory();
                Command c = commandFactory.getCommand(command);

                if (c != null){
                    String request = c.generateRequest(keyboard);
                    System.out.println(request);
                    out.println(request);

                    String response = in.nextLine();
                    c.handleResponse(response);
                }
                else{
                    System.out.println("Invalid command");
                }

            }



        } catch (UnknownHostException e) {
            System.out.println("Host unreachable: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    private static int getIntInput(Scanner keyboard) {
        try {
            return Integer.parseInt(keyboard.nextLine());
        }
        catch (NoSuchElementException e) {
            System.out.println("Invalid input");
            keyboard.nextLine();
            return -1;
        }
    }

    private static void printMenu() {
        System.out.println("0. Quit");
        System.out.println("1. Check if file exists");
        System.out.println("2. Get last modified date");
        System.out.println("3. Get first 5 lines of file");
        System.out.println("Enter choice: ");
    }

}

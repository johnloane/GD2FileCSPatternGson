package com.dkit.gd2.johnloane.server;



import com.dkit.gd2.johnloane.command.Command;
import com.dkit.gd2.johnloane.command.CommandFactory;
import com.dkit.gd2.johnloane.core.FileServiceProtocol;
import com.dkit.gd2.johnloane.core.NetworkMessage;
import com.dkit.gd2.johnloane.core.ServiceDetails;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServiceThread extends Thread{
    private Socket socket;
    private int clientCount;
    private Scanner input;
    private PrintWriter output;

    public ServiceThread(ThreadGroup threadGroup, String s, Socket socket, int clientCount) {
        super(threadGroup, s);
        this.socket = socket;
        this.clientCount = clientCount;

        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        FileServiceProtocol incomingCommand = FileServiceProtocol.NOTSET;
        String response;

        try {
            while (!incomingCommand.equals(FileServiceProtocol.QUIT)){
                NetworkMessage message = new NetworkMessage();
                message = message.readFromJSON(input.nextLine());
                System.out.println("Command received: " + message.toString());

                CommandFactory commandFactory = new CommandFactory();

                Command command = commandFactory.getCommand(message.getMessageType());

                if (command == null)
                    throw new Exception("Command not found");
                else {
                    response = command.generateResponse(message);
                    System.out.println("Response: " + response);

                    output.println(response);
                    System.out.println("Response sent to client");
                }
            }
            socket.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

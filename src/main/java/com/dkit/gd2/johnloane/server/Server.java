package com.dkit.gd2.johnloane.server;

import com.dkit.gd2.johnloane.core.ServiceDetails;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket listeningSocket = new ServerSocket(ServiceDetails.SERVER_PORT);

            ThreadGroup threadGroup = new ThreadGroup("Server threads");

            threadGroup.setMaxPriority(Thread.currentThread().getPriority() - 1);

            int clientCount = 0;
            boolean continueRunning = true;

            while (continueRunning) {
                System.out.println("Waiting for client connection request...");

                Socket socket = listeningSocket.accept();
                System.out.println("Client connected");

                ServiceThread serviceThread = new ServiceThread(threadGroup, socket.getInetAddress() + "", socket, clientCount);
                serviceThread.start();
            }

            System.out.println("Server shutting down");
            listeningSocket.close();

        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

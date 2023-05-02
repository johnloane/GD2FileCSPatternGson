package com.dkit.gd2.johnloane.command;

import com.dkit.gd2.johnloane.core.FileServiceProtocol;
import com.dkit.gd2.johnloane.core.NetworkMessage;
import com.dkit.gd2.johnloane.core.ServiceDetails;

import java.util.Scanner;

import static com.dkit.gd2.johnloane.core.Utility.getFilePathInput;

public class FirstFiveLinesCommand implements Command {
    @Override
    public String generateResponse(NetworkMessage incomingMessage) {
        String filePath = incomingMessage.getPayload();
        if (filePath.isBlank()) {
            NetworkMessage response = new NetworkMessage(FileServiceProtocol.FIRSTFIVELINES, "File path cannot be empty");
            return response.writeJSON();
        }
        if(new java.io.File(filePath).exists()) {
            try {
                Scanner fileScanner = new Scanner(new java.io.File(filePath));
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    if (fileScanner.hasNextLine()) {
                        output.append(fileScanner.nextLine());
                        output.append(ServiceDetails.BREAKING_CHARACTER);
                    }
                }
                NetworkMessage response = new NetworkMessage(FileServiceProtocol.FIRSTFIVELINES, output.toString());
                return response.writeJSON();
            } catch (java.io.FileNotFoundException e) {
                return "File not found";
            }
        }
        else {
            return "File does not exist";
        }
    }

    @Override
    public String generateRequest(Scanner keyboard) {
        String filePath = getFilePathInput(keyboard);
        NetworkMessage message = new NetworkMessage(FileServiceProtocol.FIRSTFIVELINES, filePath);
        return message.writeJSON();
    }

    @Override
    public void handleResponse(String response) {
        System.out.println("First five lines: ");
        NetworkMessage responseJson = new NetworkMessage();
        System.out.println(responseJson.readFromJSON(response).getPayload());
    }
}

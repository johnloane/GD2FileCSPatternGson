package com.dkit.gd2.johnloane.command;

import com.dkit.gd2.johnloane.core.FileServiceProtocol;
import com.dkit.gd2.johnloane.core.NetworkMessage;

import java.util.Scanner;

import static com.dkit.gd2.johnloane.core.Utility.getFilePathInput;

public class LastEditCommand implements Command {
    @Override
    public String generateResponse(NetworkMessage incomingMessage) {
        String filePath = incomingMessage.getPayload();
        if (filePath.isBlank()) {
            NetworkMessage response = new NetworkMessage(FileServiceProtocol.LASTEDIT, "File path cannot be empty");
            return response.writeJSON();
        }
        if(new java.io.File(filePath).exists()) {
            NetworkMessage response = new NetworkMessage(FileServiceProtocol.LASTEDIT, new java.util.Date(new java.io.File(filePath).lastModified()).toString());
            return response.writeJSON();
        } else {
            NetworkMessage response = new NetworkMessage(FileServiceProtocol.LASTEDIT, "File does not exist");
            return response.writeJSON();
        }
    }

    @Override
    public String generateRequest(Scanner keyboard) {
        String filePath = getFilePathInput(keyboard);
        NetworkMessage message = new NetworkMessage(FileServiceProtocol.LASTEDIT, filePath);
        return message.writeJSON();
    }

    @Override
    public void handleResponse(String response) {
        System.out.println("Last edit: ");
        NetworkMessage responseJson = new NetworkMessage();
        System.out.println(responseJson.readFromJSON(response).getPayload());
    }
}

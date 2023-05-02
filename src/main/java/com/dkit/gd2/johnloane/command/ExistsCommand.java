package com.dkit.gd2.johnloane.command;

import com.dkit.gd2.johnloane.core.FileServiceProtocol;
import com.dkit.gd2.johnloane.core.NetworkMessage;


import java.util.Scanner;
import java.io.File;

import static com.dkit.gd2.johnloane.core.Utility.getFilePathInput;

public class ExistsCommand implements Command {

    @Override
    public String generateResponse(NetworkMessage incomingMessage) {
        String filePath = incomingMessage.getPayload();
        if (filePath.isBlank()) {
            return "File path cannot be empty";
        }
        //https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java found on StackOverflow;
        if(new File(filePath).exists()) {
            NetworkMessage response = new NetworkMessage(FileServiceProtocol.EXISTS, "TRUE");
            return response.writeJSON();
        } else {
            NetworkMessage response = new NetworkMessage(FileServiceProtocol.EXISTS, "FALSE");
            return response.writeJSON();
        }
    }

    @Override
    public String generateRequest(Scanner keyboard) {
        String filePath = getFilePathInput(keyboard);
        NetworkMessage message = new NetworkMessage(FileServiceProtocol.EXISTS, filePath);
        return message.writeJSON();
    }

    @Override
    public void handleResponse(String response) {
        System.out.print("File exists: ");
        NetworkMessage responseJson = new NetworkMessage();
        System.out.println(responseJson.readFromJSON(response).getPayload());
    }
}

package com.dkit.gd2.johnloane.command;

import com.dkit.gd2.johnloane.core.FileServiceProtocol;
import com.dkit.gd2.johnloane.core.NetworkMessage;
import com.dkit.gd2.johnloane.core.ServiceDetails;

import java.util.Scanner;

public class QuitCommand implements Command {

    @Override
    public String generateResponse(NetworkMessage incomingMessage) {
        NetworkMessage response = new NetworkMessage(FileServiceProtocol.QUIT, "Goodbye");
        return response.writeJSON();
    }

    @Override
    public String generateRequest(Scanner keyboard) {
        NetworkMessage message = new NetworkMessage(FileServiceProtocol.QUIT, null);
        return message.writeJSON();
    }

    @Override
    public void handleResponse(String response) {
        NetworkMessage responseJson = new NetworkMessage();
        System.out.println(responseJson.readFromJSON(response).getPayload());
    }
}

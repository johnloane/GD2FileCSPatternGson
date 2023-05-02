package com.dkit.gd2.johnloane.command;

import com.dkit.gd2.johnloane.core.NetworkMessage;

import java.util.Scanner;

public interface Command {
    public String generateResponse(NetworkMessage incomingMessage);
    public String generateRequest(Scanner keyboard);
    public void handleResponse(String response);
}

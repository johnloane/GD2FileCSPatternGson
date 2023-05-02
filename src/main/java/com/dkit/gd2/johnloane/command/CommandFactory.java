package com.dkit.gd2.johnloane.command;

import com.dkit.gd2.johnloane.core.FileServiceProtocol;
import com.dkit.gd2.johnloane.core.ServiceDetails;

public class CommandFactory {
    public  Command getCommand(FileServiceProtocol commandName) {
        return switch (commandName){
            case EXISTS -> new ExistsCommand();
            case LASTEDIT -> new LastEditCommand();
            case FIRSTFIVELINES -> new FirstFiveLinesCommand();
            case QUIT -> new QuitCommand();
            default -> null;
        };
    }
}

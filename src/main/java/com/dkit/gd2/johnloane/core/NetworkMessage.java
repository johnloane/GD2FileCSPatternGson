package com.dkit.gd2.johnloane.core;


import com.google.gson.Gson;

public class NetworkMessage
{
    private FileServiceProtocol messageType;
    private String payload;

    public NetworkMessage()
    {
        this.messageType = FileServiceProtocol.NOTSET;
        this.payload = "";
    }

    public NetworkMessage(FileServiceProtocol messageType, String payload)
    {
        this.messageType = messageType;
        this.payload = payload;
    }

    public FileServiceProtocol getMessageType()
    {
        return messageType;
    }

    public String getPayload()
    {
        return payload;
    }

    public String writeJSON()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public NetworkMessage readFromJSON(String jsonString)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, NetworkMessage.class);
    }

    public void setPayload(String payload)
    {
        this.payload = payload;
    }

    public void setMessageType(FileServiceProtocol messageType)
    {
        this.messageType = messageType;
    }

    @Override
    public String toString()
    {
        return "NetworkMessage{" +
                "messageType=" + messageType +
                ", payload='" + payload + '\'' +
                '}';
    }
}

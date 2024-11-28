package server;

import shared.Response;
import shared.Result;

import java.io.*;
import java.net.Socket;

public class PlayerConnection implements Runnable {
    private final Socket socket;
    protected String identifier;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    protected Result result;

    public PlayerConnection(Socket socket, String identifier) {

        this.socket = socket;
        this.result = new Result();
        this.identifier = identifier;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response receiveResponse() {
        try {
            return (Response) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendResponse(Response response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }
}
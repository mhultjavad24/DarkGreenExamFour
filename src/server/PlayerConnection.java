package server;

import shared.Category;
import shared.Question;
import shared.Response;
import shared.Result;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class PlayerConnection implements Runnable {
    private final Socket socket;
    protected String name;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    protected Result result;

    public PlayerConnection(Socket socket, String name) {

        this.socket = socket;
        this.result = new Result();
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
//        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
//
//            Object inputLine;
//            while ((inputLine = in.readObject()) != null) {
//                if (inputLine instanceof Response response) {
//                    if (response.getResult() != null) {
//                        System.out.println(response.getResult().getPlayer1RoundScores());
//                    } else {
//                        System.out.println(response.getCategories().getFirst().getName());
//
//                        Question q = new Question(
//                                "Vad är huvudstaden i Sverige?",
//                                new Category("Geografi"),
//                                new String[]{"Göteborg", "Stockholm", "Malmö", "Uppsala"},
//                                1  // Stockholm , index 1
//                        );
//                    }
//                } else if (inputLine instanceof String request) {
//                    if ("GET_CATEGORIES".equals(request)) {
//                        List<Category> categories = List.of(
//                                new Category("Programming"),
//                                new Category("Animals and Nature"),
//                                new Category("History")
//                        );
//                        out.writeObject(categories);
//                        out.flush();
//                    }
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                socket.close();
//              //  client.QuizServer.removeClient(this);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
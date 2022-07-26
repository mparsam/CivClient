package View;

import Model.Request;
import enums.RequestActions;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Network {
    private static final int SERVER_PORT = 7777;
    private static OutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;
    private static InputStream inputStream;
    private static ObjectInputStream objectInputStream;
    private static Socket socket;

    public static void connectToServer() {
        try {
            socket = new Socket("localhost", SERVER_PORT);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            System.out.println("Connected to " + socket);
        } catch (Exception e) {
            System.err.println("connection problem. closing client ...");
            System.exit(0);
        }
    }


    public static synchronized Request sendRequest(Request request) {
        if (!socket.isConnected()) {
            System.err.println("Server is  Down. closing the client ...");
            System.exit(0);
        }
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            objectOutputStream.reset();
            Request response = (Request) objectInputStream.readObject();
            System.out.println("Server's Response: " + response.toString() + " obj: " + response.getObj());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Request sendRequest(String reqMessage, HashMap<String, String> params) {
        Request res = sendRequest(new Request(reqMessage, params));
        return res;
    }

    public static String getActionOfReq(String reqMessage, HashMap<String, String> params) {
        return sendRequest(new Request(reqMessage, params)).action;
    }

    public static String getParamOfReq(String reqMessage, HashMap<String, String> params, String paramKey) {
        return sendRequest(new Request(reqMessage, params)).params.get(paramKey);
    }

    //
//    public static int getResponseEnumIntOf(String reqMessage, HashMap<String, String> params) {
//        return Integer.parseInt(sendRequest(new Request(reqMessage, params)).params.get(ParameterKeys.ENUM.code));
//    }
//
//    public static int getResponseEnumIntOf(Request request) {
//        return Integer.parseInt(sendRequest(request).params.get(ParameterKeys.ENUM.code));
//    }
//
    public static Object getResponseObjOf(Request request) {
        return sendRequest(request).getObj();
    }

    public static Object getResponseObjOf(String regMessage, HashMap<String, String> params) {
        Request res = sendRequest(regMessage, params);
        return res.getObj();
    }

    public static Object getResponseObjOfPanelCommand(String command) {
        Request req = new Request(RequestActions.PANEL_COMMAND.code, null, command);
        return getResponseObjOf(req);
    }

}

package com.za.networking.peertopeer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class peer {
    private static ArrayList<String> onlineIps = new ArrayList<>();
    private static ArrayList<String> portNumbers = new ArrayList<>();
	public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Enter The UserName & His port Number For this Peer");
		//String[] setupValues = bufferedReader.readLine().split(" ");
        System.out.println("Enter Port Number : ");
        Scanner scanner = new Scanner(System.in);
        InetAddress inetAddress = java.net.InetAddress.getLocalHost();
        String myIp = inetAddress.getHostAddress();
        String portNumber = scanner.nextLine();
        connectTServer(myIp, portNumber);
		Serverthread serverThread = new Serverthread(portNumber);
		serverThread.start();
		new peer().updateListenToPeers(bufferedReader, myIp, serverThread);
	}
	public void updateListenToPeers(BufferedReader bufferedReader, String username, Serverthread serverThread) throws Exception{
    System.out.println("Online Ips" + ":" + "Port Number");
	for(int i = 0;i<onlineIps.size();i++){
        System.out.println(onlineIps.get(i) + ":" + portNumbers.get(i));
    }
    System.out.println("Enter Hostname : port num");
	System.out.println("peers to recieve messeges from (s to skip)");
	String input = bufferedReader.readLine();
	String[] inputValues = input.split(" ");
	for(int i = 0;i < inputValues.length; i++){
	    String [] tmp = inputValues[i].split(":");
	    if(onlineIps.contains(tmp[0]))
        {
            if(portNumbers.get(i).equals(tmp[1])){

            }else{
                System.out.println("Wrong Input!");
                return;
            }
        }else
        {
            System.out.println("Wrong Input!");
            return;
        }
    }
	if (!input.equals("s")) for (int  i = 0; i < inputValues.length; i++) {
		String[] address = inputValues[i].split(":");
		Socket socket = null;
		try {
			socket = new Socket(address[0], Integer.valueOf(address[1]));
			new Peerthread(socket).start();
		}
		catch(Exception e) {
			if (socket != null) socket.close();
			else System.out.println("Invalid input, Skipping to next step");
		}
	}
	communication(bufferedReader,username,serverThread);
}
public void communication(BufferedReader bufferedReader, String username, Serverthread serverThread) {
	try {
		System.out.println("Now you can communicate (e to exit , c to change)");
		boolean flag = true;
		while(flag) {
			String message = bufferedReader.readLine();
			if(message.equals("e")) {
				flag = false;
				break;
			}
			else if (message.equals("c")) {
				updateListenToPeers(bufferedReader, username, serverThread);
			}
			else {
				StringWriter stringWriter = new StringWriter();
				Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder().add("username" , username).add("messege",message).build());
				serverThread.sendMessage(stringWriter.toString());
			}
		}
		System.exit(0);
	} catch (Exception e) {}
}

    private static boolean connectTServer(String ip, String portNumber) throws UnirestException {
	    String serverIp = "http://192.168.0.1";
	    String testserverIp = "http://localhost";
        HttpResponse<JsonNode> arrayResponse = Unirest.post(testserverIp + "/SW2_Assignment2/Tracker.php")
                .field("ip", ip)
                .field("port", portNumber)
                .asJson();
        JSONArray arr = arrayResponse.getBody().getArray();
        for(int i = 0;i<arr.length();i++){
            onlineIps.add(arr.getJSONObject(i).getString("ip"));
            portNumbers.add(arr.getJSONObject(i).getString("port"));
        }
	    return false;
    }

}

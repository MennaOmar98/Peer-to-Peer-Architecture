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
	
}

package com.za.networking.peertopeer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Serverthread2 extends Thread{
private Serverthread serverThread;
private Socket socket;
private PrintWriter printWriter;
public Serverthread2(Socket socket, Serverthread serverThread) {
	this.serverThread= serverThread;
	this.socket=socket;
}
public void run() {
	try {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.printWriter= new PrintWriter(socket.getOutputStream(),true);
		while(true) serverThread.sendMessage(bufferedReader.readLine());
	} catch(Exception e) { serverThread.getServerthreads2().remove(this);}
}
public PrintWriter getPrintWriter() { return printWriter;
}}

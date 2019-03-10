package com.za.networking.peertopeer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class Serverthread extends Thread {
private ServerSocket serverSocket;  
private Set<Serverthread2> serverthreads2 = new HashSet<Serverthread2>();
public Serverthread(String portnum) throws IOException {
	
	serverSocket = new ServerSocket(Integer.valueOf(portnum));
}
public void run() {
	try {
		while(true) {
			Serverthread2 serverthread2 = new Serverthread2(serverSocket.accept(),this);
			serverthreads2.add(serverthread2);
			serverthread2.start();
		}
	}catch(Exception e) {e.printStackTrace();}
	}

void sendMessage(String message) {
	try { serverthreads2.forEach(t->t.getPrintWriter().println(message));
	} catch (Exception e) { e.printStackTrace();}
}
public Set<Serverthread2> getServerthreads2() { return serverthreads2 ;}
}

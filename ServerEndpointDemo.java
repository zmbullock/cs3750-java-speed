package com.jl.tutorial.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/serverendpointdemo")
public class ServerEndpointDemo {
	
	//open connection to client
	@OnOpen
	public void handleOpen() {
		System.out.println("Client is now connected...");
	}
	
	//exchange message with client
	@OnMessage
	public String handleMessage(String message) {
		System.out.println("recieve from client: " + message);
		String replyMessage = "echo "+message;
		System.out.println("send to client: "+ replyMessage);
		
		return replyMessage;
	}
	
	//close connection
	@OnClose
	public void handleClose() {
		System.out.println("client is now disconnected..");
	}
	
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
}

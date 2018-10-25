package websocket;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;

@ServerEndpoint("/serverendpointdemo")
public class ServerEndpointDemo {
	static Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());
	
	//open connection to client
	@OnOpen
	public void handleOpen(Session userSession) {
		chatroomUsers.add(userSession);
		System.out.println("Client is now connected...");
	}
	
	//exchange message with client
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException{
		String username = (String)userSession.getUserProperties().get("username");
		 if(username == null) {
			 userSession.getUserProperties().put("username", message);
			 userSession.getBasicRemote().sendText(buildJsonData("System", "you are now connected as "+message));
		 }else {
			 Iterator<Session> iterator = chatroomUsers.iterator();
			 while(iterator.hasNext()) {
				 iterator.next().getBasicRemote().sendText(buildJsonData(username,message));
			 }
		 }
	}
	
	//close connection
	@OnClose
	public void handleClose(Session userSession) {
		chatroomUsers.remove(userSession);
		System.out.println("client is now disconnected..");
	}
	
	private String buildJsonData(String username, String message) {
		 JsonObject jsonObject = Json.createObjectBuilder().add("message", username+": "+message).build();
		 StringWriter stringWriter = new StringWriter();
		 try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			 jsonWriter.write(jsonObject);
		 }
		return stringWriter.toString();
	}
}

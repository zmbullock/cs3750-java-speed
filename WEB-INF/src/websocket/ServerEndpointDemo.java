package websocket;

import game_manager.UpdateBoardInfo;
import game_manager.GameManager;

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
        private static GameManager gameManager = new GameManager();

  //open connection to client
  @OnOpen
  public void handleOpen(Session userSession) {
    chatroomUsers.add(userSession);
    System.out.println("Client is now connected...");

    // @zmb
    UpdateBoardInfo boardInfo;

    boardInfo = gameManager.onOpen(userSession);
    if(boardInfo != null)
    {
      try
      {
        userSession.getBasicRemote().sendText(boardInfo.playersBoardString);
        boardInfo.opponentsSession.getBasicRemote().sendText(boardInfo.opponentsBoardString);
      }
      catch (IOException ioe)
      {
        System.err.println("Error sending message");
      }
    }
  }
  
  //exchange message with client
  @OnMessage
  public void handleMessage(String message, Session userSession) throws IOException{

    // @zmb
    
    UpdateBoardInfo boardInfo;
    String[] playerInfo = message.split(",");
    int playerChoice = 999;
    int playerHandIndex = 999;
    int gameStackNum = 999;
    
    try
    {
      playerChoice = Integer.parseInt(playerInfo[0]);
      if(playerInfo.length > 1)
      {
        playerHandIndex = Integer.parseInt(playerInfo[1]);
        gameStackNum = Integer.parseInt(playerInfo[2]);

        if(playerHandIndex < 0 
           || playerHandIndex > 4
           || gameStackNum < 1
           || gameStackNum > 2)
        {
          return;
        }
        
      }
    }
    catch (Exception nfe)
    {
      return;
    }

    if(playerChoice == 999)
    {
      return;
    }
    switch (playerChoice)
    {
      case 0:
        boardInfo = gameManager.getDrawUpdate(userSession);
        break;
      case 1:
        boardInfo = gameManager.getTieBreakerUpdate(userSession);
        break;
      case 2:
        if(playerHandIndex == 999 || gameStackNum == 999)
        {
          return;
        }
        boardInfo = gameManager.getMoveUpdate(userSession, playerHandIndex, gameStackNum);
        break;
      default:
        return;
    }
   
    try
    { 
    userSession.getBasicRemote().sendText(boardInfo.playersBoardString);
    boardInfo.opponentsSession.getBasicRemote().sendText(boardInfo.opponentsBoardString);
    }
    catch (IOException ioe)
    {}
    
    /* // Chatroom reference code
     String username = (String)userSession.getUserProperties().get("username");
     if(username == null) {
       userSession.getUserProperties().put("username", message);
       userSession.getBasicRemote().sendText(buildJsonData("System", "you are now connected as "+message));
     }else {
       Iterator<Session> iterator = chatroomUsers.iterator();
       while(iterator.hasNext()) {
         iterator.next().getBasicRemote().sendText(buildJsonData(username,message));
       }
     }*/
  }
  
  //close connection
  @OnClose
  public void handleClose(Session userSession) {
    /* // Chatroom reference code
    chatroomUsers.remove(userSession);
    System.out.println("client is now disconnected..");
    */

    // @zmb 
    UpdateBoardInfo boardInfo;
    
    boardInfo = gameManager.onClose(userSession);
    if(boardInfo != null)
    {
      try
      {
        boardInfo.opponentsSession.getBasicRemote().sendText(boardInfo.opponentsBoardString);
      }
      catch (IOException ioe)
      {
        System.err.println("Error sending message");
      }
      handleClose(boardInfo.opponentsSession); // Shouldn't recurse forever becuase boardInfo should be null the second time
    }
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

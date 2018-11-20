package game_manager;

import javax.websocket.Session;

public class UpdateBoardInfo
{
  public String playersBoardString;
  public String opponentsBoardString;
  public Session opponentsSession;
  
  public UpdateBoardInfo(String pbs, String obs, Session os)
  {
    playersBoardString = pbs;
    opponentsBoardString = obs;
    opponentsSession = os;
  }
}

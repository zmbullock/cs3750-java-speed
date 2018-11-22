package game_manager;

import java.util.HashMap;
import java.util.ArrayList;

import javax.websocket.Session;

import game_manager.UpdateBoardInfo;

import game_logic.Game;

public class GameManager
{
  ArrayList<Session> unpairedList;
  HashMap<Session, Game> sessionToGame;
  HashMap<Session, Integer> sessionToPlayerNumber;
  HashMap<Session, Session> sessionToOpponent;

  public GameManager()
  {
      unpairedList = new ArrayList<>();
      sessionToGame = new HashMap<>();
      sessionToPlayerNumber = new HashMap<>();
      sessionToOpponent = new HashMap<>();
  }

  public UpdateBoardInfo onOpen(Session userSession)
  {
    if(userSession == null)
    {
      return null;
    }
    unpairedList.add(userSession);
    onUserAdded();

    Game game = sessionToGame.get(userSession);
    if(game == null)
    {
      return null;
    }
    else
    {
      String boardString = game.update_board();
      String[] boards = boardString.split("T");

      addWinner(game, boards);

      int playerBoardNumber = sessionToPlayerNumber.get(userSession)-1;
      int opponentBoardNumber = (playerBoardNumber == 1) ? 0 : 1;
      return new UpdateBoardInfo(boards[playerBoardNumber], boards[opponentBoardNumber], sessionToOpponent.get(userSession));
    }
  }

  public UpdateBoardInfo onClose(Session userSession)
  {
    if(userSession == null)
    {
      return null;
    }

    Game game = sessionToGame.get(userSession);

    if(game != null)
    {
      String boardString = game.update_board();
      String[] boards = boardString.split("T");

      int playerBoardNumber = sessionToPlayerNumber.get(userSession)-1;
      int opponentBoardNumber = (playerBoardNumber == 1) ? 0 : 1;

      boards[opponentBoardNumber] += "1";
      UpdateBoardInfo retVal = new UpdateBoardInfo(boards[playerBoardNumber], boards[opponentBoardNumber], sessionToOpponent.get(userSession));

      sessionToGame.remove(userSession);
      sessionToPlayerNumber.remove(userSession);
      sessionToOpponent.remove(userSession);

      return retVal;
    }

    sessionToGame.remove(userSession);
    sessionToPlayerNumber.remove(userSession);
    sessionToOpponent.remove(userSession);
    return null;
  }

  private void onUserAdded()
  {
    while(unpairedList.size() > 1)
    {
      Game game = new Game();

      sessionToGame.put(unpairedList.get(0), game);
      sessionToPlayerNumber.put(unpairedList.get(0), 1);
      sessionToOpponent.put(unpairedList.get(0), unpairedList.get(1));

      sessionToGame.put(unpairedList.get(1), game);
      sessionToPlayerNumber.put(unpairedList.get(1), 2);
      sessionToOpponent.put(unpairedList.get(1), unpairedList.get(0));

      unpairedList.remove(1);
      unpairedList.remove(0);
    }
  }

  public UpdateBoardInfo getDrawUpdate(Session userSession)
  {
    if(userSession == null)
    {
      return null;
    }
    Game game = sessionToGame.get(userSession);
    if(game == null)
    {
      return null;
    }
    game.draw_card(sessionToPlayerNumber.get(userSession));

    String boardString = game.update_board();
    String[] boards = boardString.split("T");

    addWinner(game, boards);

    int playerBoardNumber = sessionToPlayerNumber.get(userSession)-1;
    int opponentBoardNumber = (playerBoardNumber == 1) ? 0 : 1;
    return new UpdateBoardInfo(boards[playerBoardNumber], boards[opponentBoardNumber], sessionToOpponent.get(userSession));
    
  }
  public UpdateBoardInfo getMoveUpdate(Session userSession, int player_hand_index, int game_stack_num)
  {
    if(userSession == null)
    {
      return null;
    }
    Game game = sessionToGame.get(userSession);
    if(game == null)
    {
      return null;
    }
    
    if(!game.no_moves())
    {
      try
      {
        game.perform_move(sessionToPlayerNumber.get(userSession), player_hand_index, game_stack_num);
      }
      catch (InterruptedException ie){}
    }
    
    String boardString = game.update_board();
    String[] boards = boardString.split("T");

    addWinner(game, boards);

    int playerBoardNumber = sessionToPlayerNumber.get(userSession)-1;
    int opponentBoardNumber = (playerBoardNumber == 1) ? 0 : 1;
    return new UpdateBoardInfo(boards[playerBoardNumber], boards[opponentBoardNumber], sessionToOpponent.get(userSession));
  }
  public UpdateBoardInfo getTieBreakerUpdate(Session userSession)
  {
    if(userSession == null)
    {
      return null;
    }
    Game game = sessionToGame.get(userSession);
    if(game == null)
    {
      return null;
    }

    if(game.no_moves())
    {
      game.tie_breaker();
    }

    
    Session op = sessionToOpponent.get(userSession);
    
    String boardString = game.update_board();
    String[] boards = boardString.split("T");

    addWinner(game, boards);

    int playerBoardNumber = sessionToPlayerNumber.get(userSession)-1;
    int opponentBoardNumber = (playerBoardNumber == 1) ? 0 : 1;
    return new UpdateBoardInfo(boards[playerBoardNumber], boards[opponentBoardNumber], sessionToOpponent.get(userSession));
  }

  private void addWinner(Game game, String[] boards)
  {
    int winner = game.check_for_win();
    switch (winner)
    {
      case 0:
        boards[0] += (0);
        boards[1] += (0);
        break;
      case 1:
        boards[0] += (1);
        boards[1] += (2);
        break;
      case 2:
        boards[0] += (2);
        boards[1] += (1);
        break;
      default:
        break;
    }
  }
  
}

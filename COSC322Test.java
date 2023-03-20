
package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
    private int ourTeam;  // white = 2, black = 1
    private int otherTeam;
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {	
    	
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	
    	System.out.println("Congratulations!!! "
    			+ "I am called because the server indicated that the login is successfully");
    	System.out.println("The next step is to find a room and join it: "
    			+ "the gameClient instance created in my constructor knows how!"); 
    	if(gamegui != null) {
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
    	if(messageType.equals(GameMessage.GAME_STATE_BOARD)) {
    		// Set the game board in the GUI
    		ArrayList <Integer> gameBoard = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		gamegui.setGameState(gameBoard);

//printing out the gameBoard aray to understand what the board looks like and how it's represented
    		System.out.println("here is the gameboard representation of length" + gameBoard.size());
    		for(int x: gameBoard)
    		System.out.println(x);
    	} else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
    		ArrayList <Integer> queenPosCurr = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
    		ArrayList <Integer> queenPosNext = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
    		ArrayList <Integer> arrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);    		
    	    gamegui.updateGameState(queenPosCurr, queenPosNext, arrowPos);
    	    
    	    //Calculate here and send it to the server using the GameClient.sendMoveMessage()
    	}

        else if (messageType.equalsIgnoreCase(GameMessage.GAME_ACTION_START)) { 
        	String blackUsername = (String)msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
        	String whiteUsername = (String)msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);

        	if (blackUsername.equalsIgnoreCase(userName)) {
        		ourTeam = 1;
        		otherTeam = 2;
        	}
        	else if (whiteUsername.equalsIgnoreCase(userName)) {
        		otherTeam = 1;
        		ourTeam = 2;
        	}
        	else {
        		System.out.println(userName);
        		System.err.println("Error. Our username did not match that of either the black or white player.");
        		return false;
        	}

    		System.out.println("We are on team " + (ourTeam == 1? "Black" : "White"));

    		//if we are black, we make the move first, implement that
    		}

    	return true;   	
    }
    
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class
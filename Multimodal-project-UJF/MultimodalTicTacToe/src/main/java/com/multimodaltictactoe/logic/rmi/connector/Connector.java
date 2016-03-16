package com.multimodaltictactoe.logic.rmi.connector;

import com.multimodaltictactoe.logic.rmi.listeners.ConnectorListener;
import com.multimodaltictactoe.logic.rmi.listeners.BoardServiceListener;
import com.multimodaltictactoe.logic.rmi.service.BoardServiceInterface;
import com.multimodaltictactoe.logic.rmi.service.BoardService;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.utils.Logs;
import com.multimodaltictactoe.logic.utils.Player;
import com.multimodaltictactoe.utils.Constants;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import net.sf.lipermi.net.Server;

public class Connector implements ConnectorInterface {
    private static final String IP = Constants.Logic.Others.IP;
    private static final int PORT = Constants.Logic.Others.PORT;
    
    private static final Logger logger = Logs.getLogger(Connector.class);
    
    private final Set<ConnectorListener> listeners;
    private final CallHandler callHandler;
    private int listenerId;
    private ConnectionResult connectionResult;
    
    public Connector() {
	this.callHandler = new CallHandler();
	this.listeners = new HashSet<>();
	this.listenerId = -1;
	this.connectionResult = ConnectionResult.ERROR;
    }
    
    /**
     * Essaye de se connecter sur le reseau, c'est à dire : de trouver un service existant ou de le créer.
     * Si il trouve un service existant, il s'y connecte, l'écoute et renvoie l'état du plateau.
     * Si il ne trouve pas de service, il le crée, s'y connecte, l'écoute et renvoie son état (qui sera un plateau vide).
     * Si il ne parvient pas à récupérer ou à créer un service, on renvoie 'null'.
     * @return	L'état du plateau sur le réseau (si trouvé ou créé), sinon null.
     */
    @Override
    public Player[][] connect() {
	BoardServiceInterface bsi = loadService();
	logger.log(Level.INFO, "Service chargé = {0}", bsi);
	if(bsi != null) {
	    BoardServiceListener listener = new BoardServiceListener() {
		@Override
		public void pieceChanged(Coordinate coord, Player[][] board) {
		    firePieceChanged(coord, board);
		}
		@Override
		public void boardCleared() {
		    fireBoardCleared();
		}
	    };
	    try {
		callHandler.exportObject(BoardServiceListener.class, listener);
	    } catch (LipeRMIException ex) {
		return null;
	    }
	    logger.info("Ajout de l'ecouteur interne au service");
	    this.listenerId = bsi.addListener(listener);
	    return BoardService.stringToBoard(bsi.getBoard());
	}
	else {
	    return null;
	}
    }
    
    private BoardServiceInterface loadService() {
	BoardServiceInterface bsi = getService();
	logger.log(Level.INFO, "Service récupéré (GET) = {0}", bsi);
	if(bsi != null) {
	    this.connectionResult = ConnectionResult.GET;
	    return bsi;
	}
	else {
	    bsi = createService();
	    logger.log(Level.INFO, "Service créé (CREATE) = {0}", bsi);
	    if(bsi != null) {
		this.connectionResult = ConnectionResult.CREATE;
	    }
	    return bsi;
	}
    }
    
    private BoardServiceInterface getService() {
	try {
	    Client client = new Client(IP, PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    return bsi;
	} catch (IOException ex) {
	    return null;
	}
    }
    
    private BoardServiceInterface createService() {
	try {
	    BoardServiceInterface bsi = new BoardService();
	    callHandler.registerGlobal(BoardServiceInterface.class, bsi);
	    Server server = new Server();
	    server.bind(PORT, callHandler);
	    return bsi;
	} catch (LipeRMIException | IOException ex) {
	    return null;
	}
    }
    
    @Override
    public void disconnect() {
	logger.info("Deconnexion au service : suppression de l'ecouteur");
	try {
	    Client client = new Client(IP, PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    bsi.removeListener(this.listenerId);
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, "Erreur de deconnexion : {0}", ex);
	}
    }

    private void firePieceChanged(Coordinate coord, Player[][] board) {
	logger.log(Level.INFO, "Envoie evenement \"piece changed\" : {0} | {1}", new Object[]{coord, board[coord.getTabRow()][coord.getTabColumn()]});
	for(ConnectorListener cl : this.listeners) {
	    cl.pieceChanged(coord, board);
	}
    }
    
    private void fireBoardCleared() {
	logger.info("Envoie evenement \"board cleared\"");
	for(ConnectorListener cl : this.listeners) {
	    cl.boardCleared();
	}
    }

    @Override
    public boolean addListener(ConnectorListener cl) {
	return this.listeners.add(cl);
    }

    @Override
    public boolean removeListener(ConnectorListener cl) {
	return this.listeners.remove(cl);
    }

    @Override
    public void setBoard(Coordinate coord, Player player) {
	logger.log(Level.INFO, "Set board : {0} | {1}", new Object[]{coord, player});
	try {
	    Client client = new Client(IP, PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    bsi.set(coord, player);
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, "Erreur set board : {0}", ex);
	}
    }
    
    @Override
    public void clearBoard() {
	logger.info("Clear board");
	try {
	    Client client = new Client(IP, PORT, callHandler);
	    BoardServiceInterface bsi = (BoardServiceInterface) client.getGlobal(BoardServiceInterface.class);
	    bsi.clear();
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, "Erreur clear board : {0}", ex);
	}
    }
    
    @Override
    public ConnectionResult getConnectionResult() {
	return this.connectionResult;
    }
}

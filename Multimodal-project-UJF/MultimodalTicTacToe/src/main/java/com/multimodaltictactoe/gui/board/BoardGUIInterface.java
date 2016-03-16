package com.multimodaltictactoe.gui.board;

public interface BoardGUIInterface {    
    /**
     * Ajoute un écouteur.
     * @param bl    L'écouteur à ajouter.
     * @return	    Vrai si l'écouteur n'est pas présent (et si l'ajout a fonctionné).
     */
    public boolean addListener(BoardGUIListener bl);
    
    /**
     * Supprimer un écouteur.
     * @param bl    L'écouteur à supprimer.
     * @return	    Vrai si la suppression à fonctionnée.
     */
    public boolean removeListener(BoardGUIListener bl);
}

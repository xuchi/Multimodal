package com.multimodaltictactoe.logic.speech;

import com.multimodaltictactoe.logic.utils.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.util.Pair;

public class SpeechTranslator {
    private static final String regexA = "(A|AH)";
    private static final String regexB = "(B|BAIE)";
    private static final String regexC = "(C|CES|SES|C'EST|S'EST)";
    private static final String regex1 = "(1|UN|HEIN)";
    private static final String regex2 = "(2|DEUX|DE)";
    private static final String regex3 = "(3|TROIS)";
    
    private static final List<Pair<Pattern, Coordinate>> PATTERNS = constructPatterns();
    
    public static Coordinate hypothesisToCoordinate(String hypothesis) {	
	for(Pair<Pattern, Coordinate> pair : PATTERNS) {
	    if(pair.getKey().matcher(hypothesis).matches()) {
		return pair.getValue();
	    }
	}
	return null;
    }
    
    public static Coordinate hypothesisToCoordinate(List<String> hypothesis) {
	for(String hyp : hypothesis) {
	    Coordinate coord = hypothesisToCoordinate(hyp);
	    if(coord != null) {
		return coord;
	    }
	}
	return null;
    }
    
    private static String getRegex(String regexColumn, String regexRow) {
	String verb = "(jouer|placer)";
	String term = "(en|sur)?";
	String spaces = "\\s*";
	String coord = "(" + regexColumn + spaces + regexRow + ")";
	return (verb + spaces + term + spaces + coord);
    }
    
    private static Pair<Pattern, Coordinate> construct(String regexColumn, String regexRow, Coordinate coord) {
	return new Pair<>(Pattern.compile(getRegex(regexColumn, regexRow), Pattern.CASE_INSENSITIVE), coord);
    }
    
    private static List<Pair<Pattern, Coordinate>> constructPatterns() {
	List<Pair<Pattern, Coordinate>> patternsResult = new ArrayList<>();
	for(int r = 0; r < 3; r++) {
	    for(int c = 0; c < 3; c++) {
		Coordinate coord = new Coordinate(r, c);
		String regexColumn, regexRow;
		switch(coord.getColumn()) {
		    case 'A':
			regexColumn = regexA;
			break;
		    case 'B':
			regexColumn = regexB;
			break;
		    case 'C':
			regexColumn = regexC;
			break;
		    default :
			throw new RuntimeException("Erreur construction pattern (colonne)");
		}
		switch(coord.getRow()) {
		    case 1:
			regexRow = regex1;
			break;
		    case 2:
			regexRow = regex2;
			break;
		    case 3:
			regexRow = regex3;
			break;
		    default :
			throw new RuntimeException("Erreur construction pattern (ligne)");
		}
		patternsResult.add(construct(regexColumn, regexRow, coord));
	    }
	}
	return patternsResult;
    }
    
}

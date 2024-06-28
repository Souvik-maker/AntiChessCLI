import java.io.*;
import java.lang.*;
import java.util.*;

public class Gamepiece {

	private String piece;
	private String position;
	private String player;
	private ArrayList<String> moves;
	private int movecount;

	public Gamepiece() { // Constructor
		this.piece = "";
		this.position = "";
		this.player = "";
		this.moves = new ArrayList<String>();
		movecount = 0;
	}

	// Getters and Setters
	public String getPiece() {
		return this.piece;
	}

	public boolean setPiece(String newPiece) {
		if (newPiece.length() > 1) {
			return false;
		}
		if (!newPiece.equalsIgnoreCase("p") &&
				!newPiece.equalsIgnoreCase("r") &&
				!newPiece.equalsIgnoreCase("n") &&
				!newPiece.equalsIgnoreCase("b") &&
				!newPiece.equalsIgnoreCase("q") &&
				!newPiece.equalsIgnoreCase("k")) {
			return false;
		}
		this.piece = newPiece;
		return true;
	}

	public String getPosition() {
		return this.position;
	}

	public boolean setPosition(String newPosition) {
		if (newPosition.length() != 2) {
			return false;
		}
		String file = newPosition.substring(0, 1);
		String rank = newPosition.substring(1, 2);
		char fileChar = file.charAt(0);
		char rankNum = rank.charAt(0);
		int rankInt = Character.getNumericValue(rankNum);
		if (!Character.isLetter(fileChar) || (file.compareToIgnoreCase("h") > 0)) {
			return false;
		}
		if (!Character.isDigit(rankNum) || ((rankInt < 1) || (rankInt > 8))) {
			return false;
		}
		this.position = newPosition.toLowerCase();
		return true;
	}

	public String getPlayer() {
		return this.player;
	}

	public boolean setPlayer(String newPlayer) {
		if (!newPlayer.equalsIgnoreCase("w") && !newPlayer.equalsIgnoreCase("b")) {
			return false;
		}
		this.player = newPlayer;
		return true;
	}

	public ArrayList<String> getMoves() {
		return this.moves;
	}

	public void setMoves(ArrayList<String> moveList) {
		this.moves = moveList;
	}

	public int getMoveCount() {
		return this.movecount;
	}

	public void setMoveCount(int newMC) {
		this.movecount = newMC;
	}

	// Generate all the possible moves for a given piece
	public void createMoveSet(Board currentBoard) {
		ArrayList<String> newMoveSet = new ArrayList<String>();// Pawn

		if (this.getPiece().equalsIgnoreCase("P")) {
			int pieceI = Controller.rankToInd(this.getPosition());
			int pieceJ = Controller.fileToInd(this.getPosition());
			String tempMove = "";
			if (this.getPlayer().equalsIgnoreCase("w")) { // White

				if (this.getMoveCount() == 0) { // If this is the pawns first move, it can move 2 spaces ahead
					if ((currentBoard.GBoard[pieceI + 1][pieceJ] == null) &&
							(currentBoard.GBoard[pieceI + 2][pieceJ] == null)) {
						tempMove = Controller.indToPos(pieceI + 2, pieceJ);
						this.moves.add(tempMove);
					}
				}
				if (currentBoard.GBoard[pieceI + 1][pieceJ] == null) { // If the space in front of the pawn is free, it can move there.
					tempMove = Controller.indToPos(pieceI + 1, pieceJ);
					this.moves.add(tempMove);
				}
				if ((pieceJ + 1) < 8) { // If the space to the diagonal of the pawn holds an opponent's piece, it can take it
					
				if (currentBoard.GBoard[pieceI + 1][pieceJ + 1] != null) {
						if (currentBoard.GBoard[pieceI + 1][pieceJ + 1].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(pieceI + 1, pieceJ + 1);
							this.moves.add(tempMove);
						}
					}
				}
				if ((pieceJ - 1) > -1) { // Diagonals
					
					if (currentBoard.GBoard[pieceI + 1][pieceJ - 1] != null) {
						if (currentBoard.GBoard[pieceI + 1][pieceJ - 1].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(pieceI + 1, pieceJ - 1);
							this.moves.add(tempMove);
						}
					}
				}

			} else { // Repeat asame for black
				if (this.getMoveCount() == 0) {
					if ((currentBoard.GBoard[pieceI - 1][pieceJ] == null) &&
							(currentBoard.GBoard[pieceI - 2][pieceJ] == null)) {
						tempMove = Controller.indToPos(pieceI - 2, pieceJ);
						this.moves.add(tempMove);
					}
				}
				if (currentBoard.GBoard[pieceI - 1][pieceJ] == null) {
					tempMove = Controller.indToPos(pieceI - 1, pieceJ);
					this.moves.add(tempMove);
				}
				if ((pieceJ - 1) > -1) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ - 1] != null) {
						if (currentBoard.GBoard[pieceI - 1][pieceJ - 1].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(pieceI - 1, pieceJ - 1);
							this.moves.add(tempMove);
						}
					}
				}
				if ((pieceJ + 1) < 8) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ + 1] != null) {
						if (currentBoard.GBoard[pieceI - 1][pieceJ + 1].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(pieceI - 1, pieceJ + 1);
							this.moves.add(tempMove);
						}
					}
				}
			}
		}
		if (this.getPiece().equalsIgnoreCase("R")) { // Rook
			int pieceI = Controller.rankToInd(this.getPosition());
			int pieceJ = Controller.fileToInd(this.getPosition());
			String tempMove = "";
			if (this.getPlayer().equalsIgnoreCase("w")) { // White
				for (int i = pieceI + 1; i < 8; i++) { // Check spaces to the north of rook for empty spaces or black pieces
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} 
					// If a black piece occupies a space in front of rook
					//add that space as a valid mmove, but stop iterating (rook cannot go through opponent)
					else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int i = pieceI - 1; i > -1; i--) { // Check south
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int j = pieceJ - 1; j > -1; j--) { // Checking west
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}

				for (int j = pieceJ + 1; j < 8; j++) { // Checking east
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
			} else { // Repeating for black
				for (int i = pieceI + 1; i < 8; i++) {
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int i = pieceI - 1; i > -1; i--) {
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}

				for (int j = pieceJ - 1; j > -1; j--) {
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int j = pieceJ + 1; j < 8; j++) {
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
			}
		}

		if (this.getPiece().equalsIgnoreCase("N")) { // knight
			int pieceI = Controller.rankToInd(this.getPosition());
			int pieceJ = Controller.fileToInd(this.getPosition());
			String tempMove = "";
			if (this.getPlayer().equalsIgnoreCase("w")) { // white
				int iOffset = pieceI + 2; 
				int jOffset = pieceJ + 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) { // If space is empty or occupied by opponent, it is a valid move
					
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI + 2;
				jOffset = pieceJ - 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 2;
				jOffset = pieceJ + 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 2;
				jOffset = pieceJ - 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI + 1; 
				jOffset = pieceJ + 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI + 1;
				jOffset = pieceJ - 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 1;
				jOffset = pieceJ + 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 1;
				jOffset = pieceJ - 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
			} else { // Repeat for black
				int iOffset = pieceI + 2;
				int jOffset = pieceJ + 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI + 2;
				jOffset = pieceJ - 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 2;
				jOffset = pieceJ + 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 2;
				jOffset = pieceJ - 1;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI + 1;
				jOffset = pieceJ + 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI + 1;
				jOffset = pieceJ - 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 1;
				jOffset = pieceJ + 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
				iOffset = pieceI - 1;
				jOffset = pieceJ - 2;
				if ((iOffset < 8) && (jOffset < 8) && (iOffset > -1) && (jOffset > -1)) {
					if ((currentBoard.GBoard[iOffset][jOffset] == null)
							|| currentBoard.GBoard[iOffset][jOffset].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(iOffset, jOffset);
						this.moves.add(tempMove);
					}
				}
			}
		}
		if (this.getPiece().equalsIgnoreCase("B")) { //  Bishop
			int pieceI = Controller.rankToInd(this.getPosition());
			int pieceJ = Controller.fileToInd(this.getPosition());
			String tempMove = "";
			if (this.getPlayer().equalsIgnoreCase("w")) {//  White
				for (int i = 1; i < 8; i++) { 
					int newI = pieceI + i;
					int newJ = pieceJ + i;
					// If the space is on the board and empty or contains opponent piece 
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}

				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i; // Southeast
					int newJ = pieceJ + i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI + i; // Northwest
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}

				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i; // Southwest
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
			} else { // Repeating for black
				for (int i = 1; i < 8; i++) {
					int newI = pieceI + i;
					int newJ = pieceJ + i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i;
					int newJ = pieceJ + i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}

				for (int i = 1; i < 8; i++) {
					int newI = pieceI + i;
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i;
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
			}
		}
		if (this.getPiece().equalsIgnoreCase("Q")) { // Queen
			int pieceI = Controller.rankToInd(this.getPosition());
			int pieceJ = Controller.fileToInd(this.getPosition());
			String tempMove = "";
			if (this.getPlayer().equalsIgnoreCase("w")) {
				for (int i = pieceI + 1; i < 8; i++) {
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int i = pieceI - 1; i > -1; i--) {
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int j = pieceJ - 1; j > -1; j--) {
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int j = pieceJ + 1; j < 8; j++) {
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI + i;
					int newJ = pieceJ + i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i;
					int newJ = pieceJ + i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI + i;
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i;
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("b")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
			} else {
				for (int i = pieceI + 1; i < 8; i++) {
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int i = pieceI - 1; i > -1; i--) {
					if (currentBoard.GBoard[i][pieceJ] == null) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[i][pieceJ].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(i, pieceJ);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int j = pieceJ - 1; j > -1; j--) {
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int j = pieceJ + 1; j < 8; j++) {
					if (currentBoard.GBoard[pieceI][j] == null) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						continue;
					} else if (currentBoard.GBoard[pieceI][j].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI, j);
						this.moves.add(tempMove);
						break;
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI + i;
					int newJ = pieceJ + i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i;
					int newJ = pieceJ + i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI + i;
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				for (int i = 1; i < 8; i++) {
					int newI = pieceI - i;
					int newJ = pieceJ - i;
					if ((newI < 8) && (newI > -1) && (newJ < 8) && (newJ > -1)) {
						if ((currentBoard.GBoard[newI][newJ] == null)) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							continue;
						} else if (currentBoard.GBoard[newI][newJ].getPlayer().equalsIgnoreCase("w")) {
							tempMove = Controller.indToPos(newI, newJ);
							this.moves.add(tempMove);
							break;
						} else {
							break;
						}
					} else {
						break;
					}
				}

			}
		}
		if (this.getPiece().equalsIgnoreCase("K")) { //  king
			int pieceI = Controller.rankToInd(this.getPosition());
			int pieceJ = Controller.fileToInd(this.getPosition());
			String tempMove = "";
			if (this.getPlayer().equalsIgnoreCase("w")) { // white

				if ((pieceI + 1) < 8) { // Check all adjacent spaces for empty spaces or enemy units


					if (currentBoard.GBoard[pieceI + 1][pieceJ] == null ||
							currentBoard.GBoard[pieceI + 1][pieceJ].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI + 1, pieceJ);
						this.moves.add(tempMove);
					}
				}
				if ((pieceI - 1) > -1) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ] == null ||
							currentBoard.GBoard[pieceI - 1][pieceJ].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI - 1, pieceJ);
						this.moves.add(tempMove);
					}
				}
				if ((pieceJ + 1) < 8) {
					if (currentBoard.GBoard[pieceI][pieceJ + 1] == null ||
							currentBoard.GBoard[pieceI][pieceJ + 1].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI, pieceJ + 1);
						this.moves.add(tempMove);
					}
				}
				if ((pieceJ - 1) > -1) {
					if (currentBoard.GBoard[pieceI][pieceJ - 1] == null ||
							currentBoard.GBoard[pieceI][pieceJ - 1].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI, pieceJ - 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI + 1) < 8) && ((pieceJ + 1) < 8)) {
					if (currentBoard.GBoard[pieceI + 1][pieceJ + 1] == null ||
							currentBoard.GBoard[pieceI + 1][pieceJ + 1].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI + 1, pieceJ + 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI + 1) < 8) && ((pieceJ - 1) > -1)) {
					if (currentBoard.GBoard[pieceI + 1][pieceJ - 1] == null ||
							currentBoard.GBoard[pieceI + 1][pieceJ - 1].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI + 1, pieceJ - 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI - 1) > -1) && ((pieceJ + 1) < 8)) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ + 1] == null ||
							currentBoard.GBoard[pieceI - 1][pieceJ + 1].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI - 1, pieceJ + 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI - 1) > -1) && ((pieceJ - 1) > -1)) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ - 1] == null ||
							currentBoard.GBoard[pieceI - 1][pieceJ - 1].getPlayer().equalsIgnoreCase("b")) {
						tempMove = Controller.indToPos(pieceI - 1, pieceJ - 1);
						this.moves.add(tempMove);
					}
				}
				// Castling
				if ((this.getMoveCount() == 0) && (currentBoard.GBoard[0][7] != null)) {
					if ((currentBoard.GBoard[0][7].getMoveCount() == 0) &&
							(currentBoard.GBoard[0][6] == null) &&
							(currentBoard.GBoard[0][5] == null)) {
						tempMove = Controller.indToPos(0, 6);
						this.moves.add(tempMove);
					}
				}
				if ((this.getMoveCount() == 0) && (currentBoard.GBoard[0][0] != null)) {
					if ((currentBoard.GBoard[0][0].getMoveCount() == 0) &&
							(currentBoard.GBoard[0][1] == null) &&
							(currentBoard.GBoard[0][2] == null) &&
							(currentBoard.GBoard[0][3] == null)) {
						tempMove = Controller.indToPos(0, 2);
						this.moves.add(tempMove);
					}
				}
			} else { // Repeat for black
				if ((pieceI + 1) < 8) {
					if (currentBoard.GBoard[pieceI + 1][pieceJ] == null ||
							currentBoard.GBoard[pieceI + 1][pieceJ].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI + 1, pieceJ);
						this.moves.add(tempMove);
					}
				}
				if ((pieceI - 1) > -1) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ] == null ||
							currentBoard.GBoard[pieceI - 1][pieceJ].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI - 1, pieceJ);
						this.moves.add(tempMove);
					}
				}
				if ((pieceJ + 1) < 8) {
					if (currentBoard.GBoard[pieceI][pieceJ + 1] == null ||
							currentBoard.GBoard[pieceI][pieceJ + 1].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI, pieceJ + 1);
						this.moves.add(tempMove);
					}
				}
				if ((pieceJ - 1) > -1) {
					if (currentBoard.GBoard[pieceI][pieceJ - 1] == null ||
							currentBoard.GBoard[pieceI][pieceJ - 1].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI, pieceJ - 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI + 1) < 8) && ((pieceJ + 1) < 8)) {
					if (currentBoard.GBoard[pieceI + 1][pieceJ + 1] == null ||
							currentBoard.GBoard[pieceI + 1][pieceJ + 1].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI + 1, pieceJ + 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI + 1) < 8) && ((pieceJ - 1) > -1)) {
					if (currentBoard.GBoard[pieceI + 1][pieceJ - 1] == null ||
							currentBoard.GBoard[pieceI + 1][pieceJ - 1].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI + 1, pieceJ - 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI - 1) > -1) && ((pieceJ + 1) < 8)) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ + 1] == null ||
							currentBoard.GBoard[pieceI - 1][pieceJ + 1].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI - 1, pieceJ + 1);
						this.moves.add(tempMove);
					}
				}
				if (((pieceI - 1) > -1) && ((pieceJ - 1) > -1)) {
					if (currentBoard.GBoard[pieceI - 1][pieceJ - 1] == null ||
							currentBoard.GBoard[pieceI - 1][pieceJ - 1].getPlayer().equalsIgnoreCase("w")) {
						tempMove = Controller.indToPos(pieceI - 1, pieceJ - 1);
						this.moves.add(tempMove);
					}
				}
			}
		}
	}
}
// document has been formatted for better display


import java.io.*;
import java.lang.*;
import java.util.*;

public class Controller {

	// creating the gameboard
	public static Board createBoard() {
		Board gameBoard = new Board();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				gameBoard.GBoard[i][j] = null;
			}
		}

		int i = 1;

		for (int j = 0; j < 8; j++) {

			Gamepiece temp = new Gamepiece();
			temp.setPiece("P");
			temp.setPlayer("w");
			String pos = Controller.indToPos(i, j);
			temp.setPosition(pos);
			gameBoard.GBoard[i][j] = temp;
		}

		i = 0;

		for (int j = 0; j < 8; j++) {
			Gamepiece temp = new Gamepiece();
			temp.setPlayer("w");
			String pos = Controller.indToPos(i, j);
			temp.setPosition(pos);

			if (j == 0 || j == 7) {
				temp.setPiece("R");
			}
			if (j == 1 || j == 6) {
				temp.setPiece("N");
			}
			if (j == 2 || j == 5) {
				temp.setPiece("B");
			}
			if (j == 3) {
				temp.setPiece("Q");
			}
			if (j == 4) {
				temp.setPiece("K");
			}
			gameBoard.GBoard[i][j] = temp;
		}

		i = 6;

		for (int j = 0; j < 8; j++) {
			Gamepiece temp = new Gamepiece();
			temp.setPiece("P");
			temp.setPlayer("b");
			String pos = Controller.indToPos(i, j);
			temp.setPosition(pos);
			gameBoard.GBoard[i][j] = temp;
		}

		i = 7;

		for (int j = 0; j < 8; j++) {
			Gamepiece temp = new Gamepiece();
			temp.setPlayer("b");
			String pos = Controller.indToPos(i, j);
			temp.setPosition(pos);

			if (j == 0 || j == 7) {
				temp.setPiece("R");
			}
			if (j == 1 || j == 6) {
				temp.setPiece("N");
			}
			if (j == 2 || j == 5) {
				temp.setPiece("B");
			}
			if (j == 3) {
				temp.setPiece("Q");
			}
			if (j == 4) {
				temp.setPiece("K");
			}
			gameBoard.GBoard[i][j] = temp;
		}

		i = 1;

		for (int x = 0; x < 8; x++) {
			gameBoard.GBoard[i][x].createMoveSet(gameBoard);
		}

		i = 6;

		for (int x = 0; x < 8; x++) {
			gameBoard.GBoard[i][x].createMoveSet(gameBoard);
		}

		gameBoard.GBoard[0][0].createMoveSet(gameBoard);
		gameBoard.GBoard[0][7].createMoveSet(gameBoard);
		gameBoard.GBoard[7][0].createMoveSet(gameBoard);
		gameBoard.GBoard[7][7].createMoveSet(gameBoard);

		gameBoard.GBoard[0][1].createMoveSet(gameBoard);
		gameBoard.GBoard[0][6].createMoveSet(gameBoard);
		gameBoard.GBoard[7][1].createMoveSet(gameBoard);
		gameBoard.GBoard[7][6].createMoveSet(gameBoard);

		gameBoard.GBoard[0][2].createMoveSet(gameBoard);
		gameBoard.GBoard[0][5].createMoveSet(gameBoard);
		gameBoard.GBoard[7][2].createMoveSet(gameBoard);
		gameBoard.GBoard[7][5].createMoveSet(gameBoard);

		gameBoard.GBoard[0][3].createMoveSet(gameBoard);
		gameBoard.GBoard[7][3].createMoveSet(gameBoard);

		gameBoard.GBoard[0][4].createMoveSet(gameBoard);
		gameBoard.GBoard[7][4].createMoveSet(gameBoard);

		gameBoard.makePieceList();

		return gameBoard;
	}

	// Method used in converting gameboard points into FileRank
	public static String indToPos(int row, int col) {
		String pos;
		int posRank = row + 1;
		String posFile = "";
		// Returns the position as a string in the format "file+rank".
		if (col == 0) {
			posFile = "a";
		} else if (col == 1) {
			posFile = "b";
		} else if (col == 2) {
			posFile = "c";
		} else if (col == 3) {
			posFile = "d";
		} else if (col == 4) {
			posFile = "e";
		} else if (col == 5) {
			posFile = "f";
		} else if (col == 6) {
			posFile = "g";
		} else if (col == 7) {
			posFile = "h";
		}

		pos = posFile + Integer.toString(posRank);

		return pos;
	}

	// Coverting rank to J coordinate

	public static int fileToInd(String pos) {
		int fileInd = -1;
		String posFile = pos.substring(0, 1);

		if (posFile.equalsIgnoreCase("a")) {
			fileInd = 0;
		} else if (posFile.equalsIgnoreCase("b")) {
			fileInd = 1;
		} else if (posFile.equalsIgnoreCase("c")) {
			fileInd = 2;
		} else if (posFile.equalsIgnoreCase("d")) {
			fileInd = 3;
		} else if (posFile.equalsIgnoreCase("e")) {
			fileInd = 4;
		} else if (posFile.equalsIgnoreCase("f")) {
			fileInd = 5;
		} else if (posFile.equalsIgnoreCase("g")) {
			fileInd = 6;
		} else if (posFile.equalsIgnoreCase("h")) {
			fileInd = 7;
		}

		return fileInd;
	}

	// Converting rank to I coordinate

	public static int rankToInd(String pos) {
		int rankInd = -1;
		String posRank = pos.substring(1, 2);

		if (posRank.equalsIgnoreCase("1")) {
			rankInd = 0;
		} else if (posRank.equalsIgnoreCase("2")) {
			rankInd = 1;
		} else if (posRank.equalsIgnoreCase("3")) {
			rankInd = 2;
		} else if (posRank.equalsIgnoreCase("4")) {
			rankInd = 3;
		} else if (posRank.equalsIgnoreCase("5")) {
			rankInd = 4;
		} else if (posRank.equalsIgnoreCase("6")) {
			rankInd = 5;
		} else if (posRank.equalsIgnoreCase("7")) {
			rankInd = 6;
		} else if (posRank.equalsIgnoreCase("8")) {
			rankInd = 7;
		}

		return rankInd;
	}

	// method to test if entered move is valid
	public static boolean isValidMove(String fromPos, String toPos, Board gameBoard, boolean isWhiteTurn) {

		if ((fromPos.length() != 2) || (toPos.length() != 2)) {
			System.out.println("Invalid move entry format. Please try again.");
			return false;
		}

		if ((!Character.isLetter(fromPos.charAt(0))) || (fromPos.substring(0, 1).compareToIgnoreCase("h") > 0)) {
			System.out.println("Move entry out of bounds. Please try again.");
			return false;
		}

		if ((!Character.isLetter(toPos.charAt(0))) || (toPos.substring(0, 1).compareToIgnoreCase("h") > 0)) {
			System.out.println("Move entry out of bounds. Please try again.");
			return false;
		}

		if ((!Character.isDigit(fromPos.charAt(1))) ||
				(Integer.parseInt(fromPos.substring(1, 2)) > 8) ||
				(Integer.parseInt(fromPos.substring(1, 2)) < 1)) {
			System.out.println("Move entry out of bounds. Please try again.");
			return false;
		}

		if ((!Character.isDigit(toPos.charAt(1))) ||
				(Integer.parseInt(toPos.substring(1, 2)) > 8) ||
				(Integer.parseInt(toPos.substring(1, 2)) < 1)) {
			System.out.println("Move entry out of bounds. Please try again.");
			return false;
		}

		int fromI = Controller.rankToInd(fromPos);
		int fromJ = Controller.fileToInd(fromPos);
		int toI = Controller.rankToInd(toPos);
		int toJ = Controller.fileToInd(toPos);

		if (gameBoard.GBoard[fromI][fromJ] == null) {
			System.out.println("No piece exists at filerank entered. Please try again.");
			return false;
		}

		if (isWhiteTurn && gameBoard.GBoard[fromI][fromJ].getPlayer().equalsIgnoreCase("b")) {
			System.out.println("Please select a white piece to move. Try again.");
			return false;
		}

		if (!isWhiteTurn && gameBoard.GBoard[fromI][fromJ].getPlayer().equalsIgnoreCase("w")) {
			System.out.println("Please select a black piece to move. Try again.");
			return false;
		}

		gameBoard.updateMoves();

		if (gameBoard.GBoard[fromI][fromJ].getMoves().contains(toPos)) {
			return true;
		} else {
			System.out.println("Not a valid move for that piece.");
			System.out.println("Valid moves for " + gameBoard.GBoard[fromI][fromJ].getPlayer()
					+ gameBoard.GBoard[fromI][fromJ].getPiece() + " at " + gameBoard.GBoard[fromI][fromJ].getPosition()
					+ " are:");
			for (int i = 0; i < gameBoard.GBoard[fromI][fromJ].getMoves().size(); i++) {
				System.out.print(gameBoard.GBoard[fromI][fromJ].getMoves().get(i) + " ");
			}
			System.out.println("\n");
			System.out.println("Please try again.");
			return false;
		}
	}

	// make the valid as dcided move on the gameboard
	public static Board processMove(String fromPos, String toPos, Board gameBoard, boolean isWhiteTurn) {
		Board newBoard = new Board();
		newBoard = gameBoard;

		int fromI = Controller.rankToInd(fromPos);
		int fromJ = Controller.fileToInd(fromPos);
		int toI = Controller.rankToInd(toPos);
		int toJ = Controller.fileToInd(toPos);

		if (isWhiteTurn) {
			if (!newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("P") &&
					!newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("K")) {
				Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
				int newMC = movingPiece.getMoveCount() + 1;
				movingPiece.setMoveCount(newMC);
				movingPiece.setPosition(toPos.toLowerCase());

				if (newBoard.GBoard[toI][toJ] == null) {
					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
						if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getwhitePieceList().remove(pieceIndex);
					newBoard.updateMoves();
					return newBoard;
				} else {

					int blackPieceInd = -1;
					for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
						if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(toPos)) {
							blackPieceInd = i;
							break;
						}
					}
					newBoard.getblackPiecelist().remove(blackPieceInd);

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
						if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getwhitePieceList().remove(pieceIndex);
					newBoard.getwhitePieceList().add(movingPiece.getPosition());
					newBoard.updateMoves();

					return newBoard;
				}
			}

			if (newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("P")) {
				if ((fromI == 4) && (toI == 5) && (toJ != fromJ) && (newBoard.GBoard[toI][toJ] == null)) {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
						if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getwhitePieceList().remove(pieceIndex);
					newBoard.getwhitePieceList().add(movingPiece.getPosition());
					String EPPos = Controller.indToPos(toI - 1, toJ);

					int blackPieceInd = -1;
					for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
						if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(EPPos)) {
							blackPieceInd = i;
							break;
						}
					}
					newBoard.getblackPiecelist().remove(blackPieceInd);

					newBoard.GBoard[toI - 1][toJ] = null;
					newBoard.updateMoves();

					return newBoard;
				} else if (toI == 7) {

					if (newBoard.GBoard[toI][toJ] == null) {
						Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
						int newMC = movingPiece.getMoveCount() + 1;
						movingPiece.setMoveCount(newMC);
						movingPiece.setPosition(toPos.toLowerCase());
						movingPiece.setPiece("Q");

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(pieceIndex);
						newBoard.getwhitePieceList().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					} else {
						int blackPieceInd = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(toPos)) {
								blackPieceInd = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(blackPieceInd);

						Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
						int newMC = movingPiece.getMoveCount() + 1;
						movingPiece.setMoveCount(newMC);
						movingPiece.setPosition(toPos.toLowerCase());
						movingPiece.setPiece("Q");

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(pieceIndex);
						newBoard.getwhitePieceList().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					}

				} else {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					if (newBoard.GBoard[toI][toJ] == null) {
						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(pieceIndex);
						newBoard.getwhitePieceList().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					} else {

						int blackPieceInd = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(toPos)) {
								blackPieceInd = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(blackPieceInd);

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(pieceIndex);
						newBoard.getwhitePieceList().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					}
				}
			}

			if (newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("K")) {
				if ((toI == 0) && (toJ == 6) && (newBoard.GBoard[fromI][fromJ].getMoveCount() == 0)) {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
						if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getwhitePieceList().remove(pieceIndex);
					newBoard.getwhitePieceList().add(movingPiece.getPosition());

					newBoard = Controller.processMove(Controller.indToPos(0, 7), Controller.indToPos(0, 5), newBoard,
							isWhiteTurn);
					newBoard.updateMoves();

					return newBoard;

				} else if ((toI == 0) && (toJ == 2) && (newBoard.GBoard[fromI][fromJ].getMoveCount() == 0)) {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
						if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getwhitePieceList().remove(pieceIndex);
					newBoard.getwhitePieceList().add(movingPiece.getPosition());

					newBoard = Controller.processMove(Controller.indToPos(0, 0), Controller.indToPos(0, 3), newBoard,
							isWhiteTurn);
					newBoard.updateMoves();

					return newBoard;
				} else {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					if (newBoard.GBoard[toI][toJ] == null) {
						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(pieceIndex);
						newBoard.getwhitePieceList().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					} else {

						int blackPieceInd = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(toPos)) {
								blackPieceInd = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(blackPieceInd);

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(pieceIndex);
						newBoard.getwhitePieceList().add(movingPiece.getPosition());
						newBoard.updateMoves();
						return newBoard;
					}
				}
			}
		} else {

			if (!newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("P") &&
					!newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("K")) {
				Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
				int newMC = movingPiece.getMoveCount() + 1;
				movingPiece.setMoveCount(newMC);
				movingPiece.setPosition(toPos.toLowerCase());

				if (newBoard.GBoard[toI][toJ] == null) {
					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
						if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getblackPiecelist().remove(pieceIndex);
					newBoard.getblackPiecelist().add(movingPiece.getPosition());
					newBoard.updateMoves();

					return newBoard;
				} else {

					int whitePieceInd = -1;
					for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
						if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(toPos)) {
							whitePieceInd = i;
							break;
						}
					}
					newBoard.getwhitePieceList().remove(whitePieceInd);

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
						if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getblackPiecelist().remove(pieceIndex);
					newBoard.getblackPiecelist().add(movingPiece.getPosition());
					newBoard.updateMoves();

					return newBoard;
				}
			}

			if (newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("P")) {

				if ((fromI == 3) && (toI == 2) && (toJ != fromJ) && (newBoard.GBoard[toI][toJ] == null)) {

					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
						if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getblackPiecelist().remove(pieceIndex);
					newBoard.getblackPiecelist().add(movingPiece.getPosition());

					String EPPos = Controller.indToPos(toI + 1, toJ);

					int whitePieceInd = -1;
					for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
						if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(EPPos)) {
							whitePieceInd = i;
							break;
						}
					}
					newBoard.getwhitePieceList().remove(whitePieceInd);

					newBoard.GBoard[toI + 1][toJ] = null;
					newBoard.updateMoves();

					return newBoard;
				} else if (toI == 7) {

					if (newBoard.GBoard[toI][toJ] == null) {
						Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
						int newMC = movingPiece.getMoveCount() + 1;
						movingPiece.setMoveCount(newMC);
						movingPiece.setPosition(toPos.toLowerCase());
						movingPiece.setPiece("Q");

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(pieceIndex);
						newBoard.getblackPiecelist().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					} else {
						int whitePieceInd = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(toPos)) {
								whitePieceInd = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(whitePieceInd);

						Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
						int newMC = movingPiece.getMoveCount() + 1;
						movingPiece.setMoveCount(newMC);
						movingPiece.setPosition(toPos.toLowerCase());
						movingPiece.setPiece("Q");

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(pieceIndex);
						newBoard.getblackPiecelist().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					}

				} else {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					if (newBoard.GBoard[toI][toJ] == null) {
						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(pieceIndex);
						newBoard.getblackPiecelist().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					} else {

						int whitePieceInd = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(toPos)) {
								whitePieceInd = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(whitePieceInd);

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(pieceIndex);
						newBoard.getblackPiecelist().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					}
				}
			}

			if (newBoard.GBoard[fromI][fromJ].getPiece().equalsIgnoreCase("K")) {
				if ((toI == 7) && (toJ == 6) && (newBoard.GBoard[fromI][fromJ].getMoveCount() == 0)) {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
						if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getblackPiecelist().remove(pieceIndex);
					newBoard.getblackPiecelist().add(movingPiece.getPosition());

					newBoard = Controller.processMove(Controller.indToPos(7, 7), Controller.indToPos(7, 5), newBoard,
							isWhiteTurn);
					newBoard.updateMoves();

					return newBoard;

				} else if ((toI == 7) && (toJ == 2) && (newBoard.GBoard[fromI][fromJ].getMoveCount() == 0)) {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					newBoard.GBoard[toI][toJ] = movingPiece;
					newBoard.GBoard[fromI][fromJ] = null;
					int pieceIndex = -1;
					for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
						if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
							pieceIndex = i;
							break;
						}
					}
					newBoard.getblackPiecelist().remove(pieceIndex);
					newBoard.getblackPiecelist().add(movingPiece.getPosition());

					newBoard = Controller.processMove(Controller.indToPos(7, 0), Controller.indToPos(7, 3), newBoard,
							isWhiteTurn);
					newBoard.updateMoves();

					return newBoard;
				} else {
					Gamepiece movingPiece = newBoard.GBoard[fromI][fromJ];
					int newMC = movingPiece.getMoveCount() + 1;
					movingPiece.setMoveCount(newMC);
					movingPiece.setPosition(toPos.toLowerCase());

					if (newBoard.GBoard[toI][toJ] == null) {
						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(pieceIndex);
						newBoard.getblackPiecelist().add(movingPiece.getPosition());
						newBoard.updateMoves();

						return newBoard;
					} else {

						int whitePieceInd = -1;
						for (int i = 0; i < newBoard.getwhitePieceList().size(); i++) {
							if (newBoard.getwhitePieceList().get(i).equalsIgnoreCase(toPos)) {
								whitePieceInd = i;
								break;
							}
						}
						newBoard.getwhitePieceList().remove(whitePieceInd);

						newBoard.GBoard[toI][toJ] = movingPiece;
						newBoard.GBoard[fromI][fromJ] = null;
						int pieceIndex = -1;
						for (int i = 0; i < newBoard.getblackPiecelist().size(); i++) {
							if (newBoard.getblackPiecelist().get(i).equalsIgnoreCase(fromPos)) {
								pieceIndex = i;
								break;
							}
						}
						newBoard.getblackPiecelist().remove(pieceIndex);
						newBoard.getblackPiecelist().add(movingPiece.getPosition());
						newBoard.updateMoves();
						return newBoard;
					}
				}
			}

		}

		return newBoard;
	}

}
// document has been formatted for better display




import java.util.Scanner;

public class Chess {
    public static void main(String[] args) {

        Board chessboard = Controller.createBoard();
        System.out.println("");
        chessboard.printBoard();

        System.out.println("Welcome to Chess! Created By Sovi");
        System.out.println("Players: 'w' = white | 'b' = black");
        System.out.println("Pieces: 'K' = King | 'Q' = Queen | 'R' = Rook | 'B' = Bishop | 'N' = Knight | 'P' = Pawn");
        System.out.println("");

        boolean whiteTurn = true;

        while (true) {
            Gamepiece king = new Gamepiece();

            // Finding the king and determine if checkmate or check conditions exist
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessboard.GBoard[i][j] == null) {
                        continue;
                    }

                    if (chessboard.GBoard[i][j].getPlayer().equalsIgnoreCase("w") &&
                            chessboard.GBoard[i][j].getPiece().equalsIgnoreCase("k")) {
                        king = chessboard.GBoard[i][j];
                        break;
                    }
                }
            }

            if (chessboard.isCheckmate(king, whiteTurn)) {
                System.out.println("Checkmate.");
                System.out.println("Black Wins");
                System.exit(0);
            } else if (chessboard.isCheck(king.getPosition(), whiteTurn)) {
                System.out.println("White king at " + king.getPosition() + " is in CHECK!");
            }

            chessboard.printBoard();

            if (whiteTurn) {
                System.out.println("White's turn, please enter valid move: ");
            } else {
                System.out.println("Black's turn, please enter valid move: ");
            }

            Scanner input = new Scanner(System.in);
			String userInput = input.nextLine();
			userInput = userInput.trim();
			userInput = userInput + " ";
			String[] inputTokens = userInput.split(" ");
			
			
			
			if(inputTokens.length == 1){
				if(inputTokens[0].equalsIgnoreCase("-h")){
					System.out.println("");
					continue;
				}
				
				else if(inputTokens[0].equalsIgnoreCase("quit")){
					if(whiteTurn){
						System.out.println("White Resigns");
						System.out.println("Black Wins");
						System.exit(0);
					}
					else{
						System.out.println("Black Resigns");
						System.out.println("White Wins");
						System.exit(0);
					}
				}
			}


            boolean canMove = Controller.isValidMove(inputTokens[0], inputTokens[1], chessboard, whiteTurn);
            if (!canMove) {
                System.out.println("Invalid move");
                continue;
            }

            chessboard = Controller.processMove(inputTokens[0], inputTokens[1], chessboard, whiteTurn);
            whiteTurn = !whiteTurn;
        }
		
    }
}


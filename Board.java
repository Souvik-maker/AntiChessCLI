import java.io.*;
import java.lang.*;
import java.util.*;

public class Board {

	public Gamepiece[][] GBoard;
	public ArrayList<String> blackPieceList;
	public ArrayList<String> whitePieceList;
	public int turn; 

	public Board() {// creating constructor
		this.GBoard = new Gamepiece[8][8];
		this.blackPieceList = new ArrayList<String>();
		this.whitePieceList = new ArrayList<String>();
		this.turn = 0;
	}

	public void printBoard() {// Printing the board
		for (int i = 7; i >= 0; i--) {

			for (int j = 0; j < 8; j++) {

				if (this.GBoard[i][j] == null) {

					if ((isEven(i) && isEven(j)) || (!isEven(i) && !isEven(j))) {

						System.out.print("%% ");

						if (j == 7) {

							int k = i + 1;
							System.out.print(" " + k + "\n");
							
						}

						continue;

					}

					else {

						System.out.print("   ");

						if (j == 7)
						 {
							int k = i + 1;
							System.out.print(" " + k + "\n");
						}

						continue;

					}
				}


				System.out.print(this.GBoard[i][j].getPlayer() + this.GBoard[i][j].getPiece() + " ");

				if (j == 7) 
				{
					int k = i + 1;

					System.out.print(" " + k + "\n");
				}
			}


			if (i == 0)
			 {

				System.out.println(" a  b  c  d  e  f  g  h \n");

				System.out.println(" ");

			}

		}
	}

	public boolean isEven(int i) 
	{// even checking
		if (i % 2 == 0) 
		{
			return true;
		}
		return false;
	}


	// See if a certain position is attackable by enemy piece

	public boolean isCheck(String pos, boolean white) 
	{
		int I = Controller.rankToInd(pos);
		int J = Controller.fileToInd(pos);

		if (white)
		 {

			for (String h : this.getblackPiecelist())
			 {
				int i = Controller.rankToInd(h);
				int j = Controller.fileToInd(h);

				ArrayList<String> pieceMoves = this.GBoard[i][j].getMoves();

				for (int x = 0; x < pieceMoves.size(); x++) 
				{
					if (pieceMoves.get(x).equalsIgnoreCase(pos)) 
					{
						return true;
					}
				}

			}
			return false;
		}
		return white;

	}

	// Method to find checkmate condition
	public boolean isCheckmate(Gamepiece king, boolean isWhite)
	 {

		if (!this.isCheck(king.getPosition(), isWhite)) 
		{
			return false;
		}

		for (String h : king.getMoves())
		 {
			if (!this.isCheck(h, isWhite))
			 {
				return false;
			}
		}

		return true;
	}

	// Generate black and white piece lists
	public void makePieceList() 
	{
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				if (this.GBoard[i][j] == null) 
				{
					continue;
				} 
				else 
				{
					if (this.GBoard[i][j].getPlayer().equalsIgnoreCase("w"))
					 {
						this.whitePieceList.add(this.GBoard[i][j].getPosition());
					}
					 else 
					 {
						this.blackPieceList.add(this.GBoard[i][j].getPosition());
					}
				}
			}
		}
	}

	public ArrayList<String> getblackPiecelist() 
	{
		return this.blackPieceList;
	}

	public ArrayList<String> getwhitePieceList() 
	{
		return this.whitePieceList;
	}

	public void updateMoves() 
	{
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				if (this.GBoard[i][j] != null) 
				
				{
					ArrayList<String> newML = new ArrayList<String>();

					this.GBoard[i][j].setMoves(newML);

					this.GBoard[i][j].createMoveSet(this);
				}

			}
		}
	}
}



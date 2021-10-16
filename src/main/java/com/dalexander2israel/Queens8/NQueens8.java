package com.dalexander2israel.Queens8;
import java.util.Random;

//imports
import org.slf4j.*;
////////////////////////////////////////////////////////////////
/** 
 * @author Dellius Alexander
 * #subject CSCI 4307
 * #Professor Ken
 * The eight queens puzzle is the problem of placing eight 
 * chess queens on an 8×8 chessboard so that no two queens 
 * threaten each other; thus, a solution requires that no 
 * two queens share the same row, column, or diagonal.
 * The NQueens8 class attempts to solve the problem with the
 * of backtracking.
 * 
 * - Game Board[8][8] 
 * */
////////////////////////////////////////////////////////////////
public class NQueens8 {
	final int N;
	int[][] board;
	/**
	 * Initialized 8 x 8 game board
	 */
	NQueens8(){this.N=8; this.board = new int[N][N];}
	/**
	 * Initialized game board of size N; user defined.
	 * @param n the size of the N Queens game board
	 */
	NQueens8(int n){this.N=n; this.board = new int[N][N];}
	/**
	 * Prints the solution game board
	 * @param board the game board
	 * @return the string representation of the game board
	 */
	public String printGameBoard(int board[][])
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				sb.append("[" + board[i][j]
								+ "]");
				sb.append("\n");
		}
		return sb.toString();
	}
	/**
	 * Prints the game board
	 * @return the string representation of the game board
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				sb.append("[" + board[i][j]
								+ "]");
				sb.append("\n");
		}
		return sb.toString();
	}
	/**
	 * Checks if a queen can be placed on the board.
	 * It also checks when queens are paced from 0 
	 * onwards in the columns. So, we will be checking 
	 * from the right to left.
	 * @param board the game board
	 * @param row	row
	 * @param col	column
	 * @return true if no queen its safe to place a queen at this 
	 * location
	 */
	boolean isSafe(int board[][], int row, int col)
	{
		int i, j;

		// Check this row left side to right side
		for (i = 0; i < col; i++){
			if (board[row][i] == 1)
				return false;
		}
		// Check upper diagonal on left side
		// set the lower boundary to 0 and count down from i,j
		for (i = row, j = col; i >= 0 && j >= 0; i--, j--){
			if (board[i][j] == 1)
				return false;
		}
		// Check lower diagonal on left side
		// set the upper boundary to N and count up 
		for (i = row, j = col; j >= 0 && i < N; i++, j--){
			if (board[i][j] == 1)
				return false;
		}
		return true;
	}
	/**
	 * The recursive function accepts a board and the 
	 * next columns to check. When all columns are filled.
	 * It return true if a solution was found or false.
	 * It also serves to backtrack a failed attempt, when it 
	 * return false.
	 * @param board the game board
	 * @param col the column being evaluated to find the best
	 * row to place a Queen
	 * @return false if a queen cannot be placed in the column; 
	 * OR true if all queens have been placed
	 */
	boolean solveNQueens(int board[][], int col)
	{
		/* base case: If all queens are placed
		then return true */
		if(col >= N)
			return true;
		///////////////////////////////////////////////////
		Random r = new Random();
		/* Consider this column and try placing
		this queen in all rows one by one */
		for (int i = 0; i < N; i++) {
			// Check if the queen can be placed on
			// board[i][col] 
			// int n = r.nextInt(((N+0)%2)+1);
			if (isSafe(board, i, col)) {
				/* Place this queen in board[i][col] */
				board[i][col] = 1;

				/* recursively try to place the next best queens */
				if (solveNQueens(board, col + 1) == true)
					return true;

				/* if isSafe(board, i, col) returns false,
					then we backtrack and change our last value*/
				board[i][col] = 0; 
			}
		}

		/* If the queen can not be placed in any row in
		this colum col, then return false */
		return false;
	}
	/**
	 * Initialize the game board with all 0's.
	 * @param board  the game board
	 * @return	the game board
	 */
	private int[][] initBoard(int[][] board)
	{
		int n = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = n;
			}
		}
		return board;
	}
	/**
	 * This function solves the N Queen problem using
	 * Backtracking. It uses solveNQueens() to 
	 * solve the problem. It returns false if queens
	 * cannot be placed, otherwise, return true and
	 * prints placement of queens in the form of 1s.
	 * Please note that there may be more than one
	 * solutions, this function prints one of the
	 * feasible solutions.
	 * @return the completed game board
	 **/
	public int[][] solveNQ()
	{
		
		this.board = initBoard(this.board);
		
		if (solveNQueens(board,0) == false) {
			System.out.print("Solution does not exist");
			// return false;
		}
		System.out.println(printGameBoard(board));
		// return the completed board
		return this.board;
	}

	/**
	 * Driver for NQueens8 puzzle
	 * @param args the CLi arguments
	 */
	public static void main(String args[])
	{
		NQueens8 Queen = new NQueens8();
		Queen.solveNQ();
		Queen.toString();
	}
}


package acm;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Royal Threat
 * Problem C
 * 
 * 1997 Mid-Atlantic Regional Programming Contest
 */
public class RoyalThreat {
	
	// Application
	
	public static void main(String[] args) {
		process(new Scanner(System.in));	
	}

	public static void process(Scanner in) {
		
		// Read Input
		Board[] boards = new Board[in.nextInt()];
		for(int i = 0; i < boards.length; i++){
			int pieces = in.nextInt();
			Board board = new Board();
			for (int p = 0; p < pieces; p++) {
				Piece piece = new Piece(in.next());
				board.add(in.nextInt(), in.nextInt(), piece);
			}
			boards[i] = board;
		}
		
		// Print Output
		for(int i = 0; i < boards.length; i++){
			System.out.printf("CONFIGURATION %d: %d PIECES THREATENED\n", i+1, boards[i].getThreats());	
		}
		System.out.println("END OF OUTPUT");
	}
	
	// API
	
	static class Board {
		
		private Position[][] positions;
		private ArrayList<Piece> pieces;
		
		public Board() {
			this.positions = new Position[8][8];
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					this.positions[r][c] = new Position(this, r+1, c+1);
				}
			}
			this.pieces = new ArrayList<Piece>();
		}
		
		public Position at(int row, int col) {
			if ((row < 1 || row > 8) || (col < 1 || col > 8)) {
				return null;
			}
			return this.positions[row-1][col-1];
		}
		
		public void add(int row, int col, Piece piece) {
			Position pos = this.positions[row-1][col-1];
			pos.setOccupant(piece);
			piece.setPosition(pos);
			this.pieces.add(piece);
		}
		
		public int getThreats() {
			ArrayList<Piece> threats = new ArrayList<Piece>(); 
			for (Piece piece : this.pieces) {
				List<Position> moves = piece.getMoves();
				for (Position move : moves) {
					if (!move.isEmpty() && !threats.contains(move.getOccupant()))
						threats.add(move.getOccupant());
				}
			}
			return threats.size();
		}
		
		static class Position {
			
			private int row;
			private int column;
			private Board board;
			private Piece occupant;
			
			private Position(Board board, int row, int column) {
				this.row = row;
				this.column = column;
				this.board = board;
				this.occupant = null;
			}

			public int getRow() {
				return this.row;
			}

			public int getColumn() {
				return this.column;
			}
			
			public Board getBoard() {
				return this.board;
			}
			
			public Piece getOccupant() {
				return this.occupant;
			}


			public void setOccupant(Piece piece) {
				this.occupant = piece;
			}
			
			public boolean isEmpty() {
				return (this.occupant == null);
			}
			
			public Position getRelativePosition(int rowOffset, int colOffset) {
				return this.getBoard().at(this.getRow()+rowOffset, this.getColumn()+colOffset);
			}
			
			public List<Board.Position> getRelativePositions(int rowOffset, int colOffset) {
				Position cur = this;
				ArrayList<Board.Position> moves = new ArrayList<Board.Position>();
				while ((cur = cur.getRelativePosition(rowOffset, colOffset)) != null) { moves.add(cur); }
				return moves;
			}
			
			public List<Position> getAdjacentPositions() {
				int cr = this.getRow();
				int cc = this.getColumn();
				Board board = this.getBoard();
				ArrayList<Position> moves = new ArrayList<Position>(8);
				for (int r = cr-1; r <= cr+1; r++) {
					for (int c = cc-1; c <= cc+1; c++) {
						Position pos = board.at(r, c);
						if (pos != null && pos != this)
							moves.add(pos);
					}
				}
				return moves;
			}
		}
	}
	
	static class Piece {
		
		private String	 		name;
		private Board.Position	position;
		
		public Piece(String name) {
			this.name = name;
			this.position = null;
		}
		
		public String getName() {
			return this.name;
		}

		public Board.Position getPosition() {
			return this.position;
		}
		
		public void setPosition(Board.Position position) {
			this.position = position;
		}
		
		public List<Board.Position> getMoves() {
			Board.Position cur = this.getPosition();
			if (cur != null) {
				String name = this.getName();
				if (name.equals("G")) {
					return cur.getAdjacentPositions();
				}
				ArrayList<Board.Position> moves = new ArrayList<Board.Position>();
				if (name.equals("K")) {
					this.addMove(moves, cur.getRelativePosition(-2, 1));
					this.addMove(moves, cur.getRelativePosition(-2, -1));
					this.addMove(moves, cur.getRelativePosition(-1, 2));
					this.addMove(moves, cur.getRelativePosition(-1, -2));
					this.addMove(moves, cur.getRelativePosition(1, 2));
					this.addMove(moves, cur.getRelativePosition(1, -2));
					this.addMove(moves, cur.getRelativePosition(2, 1));
					this.addMove(moves, cur.getRelativePosition(2, -1));
				} else {
					if (!name.equals("B")) {
						this.addRelativeMoves(moves, 0, 1);
						this.addRelativeMoves(moves, 1, 0);
						this.addRelativeMoves(moves, 0, -1);
						this.addRelativeMoves(moves, -1, 0);
					}
					if (!name.equals("R")) {
						this.addRelativeMoves(moves, 1, 1);
						this.addRelativeMoves(moves, 1, -1);
						this.addRelativeMoves(moves, -1, 1);
						this.addRelativeMoves(moves, -1, -1);
					}
				}
				return moves;
			}
			return new ArrayList<Board.Position>();
		}
		
		private void addMove(List<Board.Position> moves, Board.Position pos) { if (pos != null) { moves.add(pos); } }
		
		private void addRelativeMoves(List<Board.Position> moves, int rowOffset, int colOffset) {
			List<Board.Position> positions = this.getPosition().getRelativePositions(rowOffset, colOffset);
			for (Board.Position pos : positions) {
				moves.add(pos);
				if (!pos.isEmpty())
					break;
			}
		}
		
	}
}
package com.jaxson.board.move;

import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.ui.board.Board;
import com.jaxson.util.MyArrayList;

public class Move
{
	private IntPiece origin;
	private MyArrayList<PieceMove> pieceMoves;

	public Move(IntPiece spot)
	{
		this(spot, null);
	}

	public Move(IntPiece newSpot, IntPiece oldSpot)
	{
		origin = newSpot;
		pieceMoves = new MyArrayList<>();
		add(new PieceMove(newSpot, oldSpot));
	}

	public void add(PieceMove move)
	{
		if (move == null) return;
		if (move.isEmpty()) return;
		if (move.overwritesFriendly()) return;
		pieceMoves.add(move);
	}

	public IntPiece getOrigin()
	{
		return origin;
	}

	public Boolean isEmpty()
	{
		for (PieceMove move: pieceMoves)
		{
			if (!move.isEmpty()) return false;
		}
		return true;
	}

	public void move(Board board)
	{
		for (PieceMove move: pieceMoves)
		{
			move.move(board);
		}
	}

	public void move(IntBoard board)
	{
		for (PieceMove move: pieceMoves)
		{
			move.move(board);
		}
	}

	public Boolean overwritesFriendly()
	{
		for (PieceMove move: pieceMoves)
		{
			if (!move.overwritesFriendly()) return false;
		}
		return true;
	}

	public void remove(int index)
	{
		pieceMoves.remove(index);
	}

	public void remove(PieceMove move)
	{
		pieceMoves.remove(move);
	}

	public void setOrigin(IntPiece value)
	{
		origin = value;
	}

	@Override
	public String toString()
	{
		String string = "\n";
		string += "MOVE: " + origin.location.x + ", " + origin.location.y;
		for (PieceMove move: pieceMoves)
		{
			string += "\n";
			string += move.toString();
		}
		return string;
	}

	public void undo(Board board)
	{
		for (PieceMove move: pieceMoves)
		{
			move.undo(board);
		}
	}
}
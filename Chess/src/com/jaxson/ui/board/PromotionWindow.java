package com.jaxson.ui.board;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.jaxson.geom.Point;
import com.jaxson.ui.Dialog;
import com.jaxson.ui.Panel;
import com.jaxson.ui.ScaleContainer;
import com.jaxson.ui.Window;

public class PromotionWindow<T extends Window> extends Dialog
{
	private static final int SIZE = 2;

	private Spot[][] spots;
	private ScaleContainer scaleContainer;
	private Panel panel;
	private JLabel label;
	private int result;

	public PromotionWindow(int width, int height, int color, T window)
	{
		super(width, height, window);
		setTitle("Chess");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new CloseAdapter(this));

		label = new JLabel("Promote your pawn.", SwingConstants.CENTER);
		label.setFont(new Font(label.getName(), Font.PLAIN, 30));
		add(label, BorderLayout.PAGE_END);

		panel = new Panel(new GridLayout(SIZE, SIZE));
		scaleContainer = new ScaleContainer<>(panel);
		add(scaleContainer, BorderLayout.CENTER);

		spots = new Spot[SIZE][SIZE];
		for (int y = 0; y < SIZE; y ++)
		{
			for (int x = 0; x < SIZE; x ++)
			{
				spots[x][y] = new Spot(new Point(x, y));
				spots[x][y].addMouseListener(new PieceAdapter(spots[x][y], this));
				panel.add(spots[x][y]);
			}
		}
		spots[0][0].createPiece(Piece.QUEEN, color);
		spots[1][0].createPiece(Piece.ROOK, color);
		spots[0][1].createPiece(Piece.BISHOP, color);
		spots[1][1].createPiece(Piece.KNIGHT, color);

		draw();
		setVisible(true);
	}

	public void close()
	{
		setResult(Piece.QUEEN);
		dispose();
	}

	public int getResult()
	{
		return result;
	}

	public void onSelect(Spot spot)
	{
		setResult(spot.getPiece().type);
		dispose();
	}

	public void setResult(int value)
	{
		result = value;
	}
}

class CloseAdapter extends WindowAdapter
{
	private PromotionWindow window;

	public CloseAdapter(PromotionWindow window)
	{
		this.window = window;
	}

	public void windowClosing(WindowEvent e)
	{
		window.close();
	}
}

class PieceAdapter extends MouseAdapter
{
	private Spot object;
	private PromotionWindow window;

	public PieceAdapter(Spot object, PromotionWindow window)
	{
		this.object = object;
		this.window = window;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		object.holdSelect();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		object.select();
		window.onSelect(object);
	}
}
package com.jaxson.lib.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener<T> implements ActionListener
{
	private T object;

	public ButtonListener(T object)
	{
		this.object = object;
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		System.out.println(event);
	}
}

package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.Display;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.util.Printer;

public class GameAccelerometer implements Accelerometer
{
	private static final float ALPHA = 0.2f;
	public static final float BALANCE = 0f;
	public static final float DEAD_ZONE = 0.175f;

	private Accelerometer accelerometer;
	private Vector3 alpha;
	private Vector3 deadZone;
	private Vector3 values;

	public GameAccelerometer(Accelerometer accelerometer)
	{
		this(accelerometer, new Vector3(ALPHA, ALPHA, ALPHA));
	}

	public GameAccelerometer(Accelerometer accelerometer, Vector3 alpha)
	{
		this(accelerometer, alpha,
				new Vector3(DEAD_ZONE, DEAD_ZONE, DEAD_ZONE));
	}

	public GameAccelerometer(Accelerometer accelerometer,
			Vector3 alpha, Vector3 deadZone)
	{
		this.accelerometer = accelerometer;
		this.alpha = alpha;
		this.deadZone = deadZone;
		this.values = new Vector3();
	}

	public Vector3 alpha()
	{
		return alpha;
	}

	public Vector3 deadZone()
	{
		return deadZone;
	}

	@Override
	public boolean exists()
	{
		return accelerometer.exists();
	}

	@Override
	public boolean tiltsBackward()
	{
		return y() < -deadZone().y;
	}

	@Override
	public boolean tiltsDown()
	{
		return z() < -deadZone().z;
	}

	@Override
	public boolean tiltsForward()
	{
		return y() > deadZone().y;
	}

	@Override
	public boolean tiltsLeft()
	{
		return x() < -deadZone().x;
	}

	@Override
	public boolean tiltsRight()
	{
		return x() > deadZone().x;
	}

	@Override
	public boolean tiltsUp()
	{
		return z() > deadZone().y;
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("Values", values()),
				new Printer.Label("Alpha", alpha()),
				new Printer.Label("Dead Zone", deadZone())).toString();
	}

	@Override
	public Vector3 values()
	{
		return new Vector3(x(), y(), z());
	}

	@Override
	public float x()
	{
		return values.x + alpha.x * (accelerometer.x() - values.x);
	}

	@Override
	public float y()
	{
		return values.y + alpha.y * (accelerometer.y() - values.y);
	}

	@Override
	public float z()
	{
		return values.z + alpha.z * (accelerometer.z() - values.z);
	}

	@Override
	public void update(float dt)
	{
		accelerometer.update(dt);
		if (!exists()) return;
		this.values.x = accelerometer.x();
		this.values.y = accelerometer.y();
		this.values.z = accelerometer.z();
	}
}

package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.entities.Entity;
import com.jaxson.lib.gdx.sprites.Sprite;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.lib.gdx.states.stage.*;
import com.jaxson.lib.gdx.util.MyInputProcessor;
import com.jaxson.lib.util.MyArrayList;

public abstract class State<C extends Camera>
{
	private static final boolean CURSOR_CATCHED = true;

	private C camera;
	private GameStateManager gameStateManager;
	private InputProcessor input;
	private Stage3D stage3D;
	private Stage2D stage2D;

	public State(GameStateManager gameStateManager)
	{
		this(gameStateManager, null);
	}

	public State(GameStateManager gameStateManager, C camera)
	{
		this.gameStateManager = gameStateManager;
		this.camera = camera;
		this.stage3D = new Stage3D();
		this.stage2D = new Stage2D();

		setCursorCatched(CURSOR_CATCHED);
		setInputProcessor(new MyInputProcessor());
	}

	public void add(Entity entity)
	{
		stage3D.add(entity);
	}

	public void add(Sprite sprite)
	{
		stage2D.add(sprite);
	}

	public void dispose()
	{
		stage3D.dispose();
		stage2D.dispose();
	}

	public float getAspectRatio()
	{
		return (float)(getWidth()) / (float)(getHeight());
	}

	public C getCamera()
	{
		return camera;
	}

	public int getHeight()
	{
		return Gdx.graphics.getHeight();
	}

	public int getWidth()
	{
		return Gdx.graphics.getWidth();
	}

	protected abstract void input();

	public boolean isCursorCatched()
	{
		return Gdx.input.isCursorCatched();
	}

	public void remove(Entity entity)
	{
		stage3D.remove(entity);
	}

	public void remove(Sprite sprite)
	{
		stage2D.remove(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		stage3D.render(modelBatch, camera);
		stage2D.render(spriteBatch);
	}

	public void setCamera(C camera)
	{
		this.camera = camera;
	}

	public void setCursorCatched(boolean catched)
	{
		Gdx.input.setCursorCatched(catched);
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		this.input = inputProcessor;
		Gdx.input.setInputProcessor(input);
	}

	public void toggleCursorCatched()
	{
		setCursorCatched(!isCursorCatched());
	}

	public void update(float dt)
	{
		input();
		stage3D.update(dt);
		stage2D.update(dt);
		camera.update();
		MyInputProcessor.update(dt);
	}
}

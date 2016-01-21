package com.jaxson.lib.gdx.states.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.MyEnvironment;

public class ModelRenderer extends Renderer<Entity>
{
	private MyEnvironment environment;

	public ModelRenderer()
	{
		this(new MyEnvironment());
	}

	public ModelRenderer(MyEnvironment environment)
	{
		super();
		this.environment = environment;
	}

	public MyEnvironment getEnvironment()
	{
		return environment;
	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		if (isEmpty()) return;
		checkAgruments(spriteBatch, modelBatch, camera);
		environment.render(objects, camera);
		modelBatch.begin(camera);
		for (Entity entity: objects)
		{
			if (entity.isVisible(camera)) modelBatch.render(entity.getModelInstance(), environment);
		}
		modelBatch.end();
	}

	public void setEnvironment(MyEnvironment environment)
	{
		this.environment = environment;
	}
}
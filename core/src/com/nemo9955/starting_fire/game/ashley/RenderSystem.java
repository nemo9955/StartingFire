package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nemo9955.starting_fire.storage.SF;

public class RenderSystem extends IteratingSystem {

	private ComponentMapper<CPosition>	cp		= ComponentMapper.getFor(CPosition.class);
	private ComponentMapper<CTexture>	ct		= ComponentMapper.getFor(CTexture.class);
	// private ComponentMapper<CWorld> cw = ComponentMapper.getFor(CWorld.class);

	public boolean						render	= true;

	@SuppressWarnings("unchecked")
	public RenderSystem() {
		super(Family.getFor(CPosition.class, CTexture.class/* , CWorld.class */));
		System.out.println("created renderer");
	}

	@Override
	public void processEntity( Entity entity, float deltaTime ) {
		CPosition po = cp.get(entity);
		CTexture tex = ct.get(entity);
		// CWorld wo = cw.get(entity);

		SF.spritesBatch.draw(tex.tex, po.x, po.y/* , wo.world.hexWidht, wo.world.hexHeight */);
		System.out.println("rendering entity !!!");
	}

	@Override
	public boolean checkProcessing() {
		return render;
	}
}

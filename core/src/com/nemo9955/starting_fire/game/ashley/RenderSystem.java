package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.SF;

public class RenderSystem extends IteratingSystem {

	private ComponentMapper<CPosition>	cp		= ComponentMapper.getFor(CPosition.class);
	private ComponentMapper<CTexture>	ct		= ComponentMapper.getFor(CTexture.class);
	private ComponentMapper<CAnimation>	ca		= ComponentMapper.getFor(CAnimation.class);
	private ComponentMapper<CWorld>		cw		= ComponentMapper.getFor(CWorld.class);

	public boolean						render	= true;

	@SuppressWarnings("unchecked")
	public RenderSystem() {
		super(Family.getFor(ComponentType.getBitsFor(CPosition.class, CWorld.class),
					ComponentType.getBitsFor(CTexture.class, CAnimation.class),
					ComponentType.getBitsFor()));
	}

	@Override
	public void processEntity( Entity entity, float deltaTime ) {
		CPosition po = cp.get(entity);
		CWorld wo = cw.get(entity);
		CTexture tex = ct.get(entity);
		CAnimation anim = ca.get(entity);
		if ( tex != null ) {
			for (TextureRegion texture : tex.tex)
				SF.spritesBatch.draw(texture, po.x, po.y, texture.getRegionWidth() * wo.world.scaling, texture.getRegionHeight()
							* wo.world.scaling);
		}
		if ( anim != null ) {
			TextureRegion draw = anim.anim.getKeyFrame(wo.world.stateTime, true);
			SF.spritesBatch.draw(draw, po.x, po.y, draw.getRegionWidth() * wo.world.scaling, draw.getRegionHeight() * wo.world.scaling);
		}

	}

	@Override
	public boolean checkProcessing() {
		return render;
	}
}

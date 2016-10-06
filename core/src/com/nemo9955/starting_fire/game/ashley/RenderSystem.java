package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CInfo;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.storage.SF;

public class RenderSystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	private Family					family	= Family.all(CPosition.class, CInfo.class).one(CTexture.class, CAnimation.class, CCollision.class).get();
	public boolean					render	= true;
	private ImmutableArray<Entity>	entities;

	public RenderSystem() {
		super(0);
	}

	@Override
	public void addedToEngine( Engine engine ) {
		entities = engine.getEntitiesFor(family);
	}

	@Override
	public void removedFromEngine( Engine engine ) {
		entities = null;
	}

	@Override
	public void update( float deltaTime ) {

//		for (int i = 0; i < entities.size(); i++) {
//			Entity entity = entities.get(i);
//			CPosition po = CM.Pos.get(entity);
//			CTexture tex = CM.Tex.get(entity);
//
//			if ( tex != null ) {
//				// for (TextureRegion texture : tex.tex)
//				TextureRegion texture = tex.tex.first();
//				SF.spritesBatch.draw(texture, po.x, po.y, texture.getRegionWidth(), texture.getRegionHeight());
//			}
//		}
		
		//TODO MAKE sure everything renders in the correct order
		
		
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			CPosition po = CM.Pos.get(entity);
			CTexture tex = CM.Tex.get(entity);
			if ( tex != null )
				for (int t = 0; t < tex.size; t++) {
					TextureRegion texture = tex.get(t);
					SF.spritesBatch.draw(texture, po.x, po.y, texture.getRegionWidth(), texture.getRegionHeight());
				}

		}

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			CPosition po = CM.Pos.get(entity);
			CInfo wo = CM.Info.get(entity);
			CAnimation anim = CM.Anim.get(entity);

			if ( anim != null )
				SF.spritesBatch.draw(anim.anim.getKeyFrame(wo.world.stateTime, true), po.x, po.y);
		}

	}

	@Override
	public boolean checkProcessing() {
		return render;
	}
}

package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.SF;

public class RenderSystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	private Family family = Family.all(CPosition.class, CWorld.class)
			.one(CTexture.class, CAnimation.class, CCollision.class).get();
	public boolean render = true;
	private ImmutableArray<Entity> entities;

	public RenderSystem() {
		super(0);
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(family);
	}

	@Override
	public void removedFromEngine(Engine engine) {
		entities = null;
	}

	@Override
	public void update(float deltaTime) {

		// for (int i = 0; i < entities.size(); i++) {
		// Entity entity = entities.get(i);
		// CPosition po = CMap.position.get(entity);
		// CTexture tex = CMap.texture.get(entity);
		//
		// if ( tex != null ) {
		// // for (TextureRegion texture : tex.tex)
		// TextureRegion texture = tex.first();
		// SF.spritesBatch.draw(texture, po.x, po.y, texture.getRegionWidth(),
		// texture.getRegionHeight());
		// }
		// }

		// TODO MAKE sure everything renders in the correct order

		for (Entity entity : entities) {
			// for (int i = 0; i < entities.size(); i++) {
			// Entity entity = entities.get(i);
			CPosition po = CMap.position.get(entity);
			CTexture tex = CMap.texture.get(entity);
			if (tex != null)
				for (int t = 0; t < tex.size; t++) {
					TextureRegion texture = tex.get(t);
					SF.spritesBatch.draw(texture, po.x, po.y, texture.getRegionWidth(), texture.getRegionHeight());
				}

		}

		for (Entity entity : entities) {
			// for (int i = 0; i < entities.size(); i++) {
			// Entity entity = entities.get(i);
			CPosition po = CMap.position.get(entity);
			CWorld wo = CMap.world.get(entity);
			CAnimation anim = CMap.animation.get(entity);

			if (anim != null) {
//				System.out.println(System.currentTimeMillis());
//				float tim = ((float) System.currentTimeMillis() / 1000);
//				System.out.println(tim);
				// System.out.println( SF.gameplay.getWorld().stateTime );
				anim.stateTime += deltaTime;
				SF.spritesBatch.draw(anim.anim.getKeyFrame(anim.stateTime, true), po.x, po.y);
			}
		}

	}

	@Override
	public boolean checkProcessing() {
		return render;
	}
}

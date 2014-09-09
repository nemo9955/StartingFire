package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.RenderSystem;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CInteract;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.world.WorldGenerator.GenType;
import com.nemo9955.starting_fire.storage.SF;

public class World implements Disposable {

	PooledEngine	eng			= new PooledEngine();
	EntityManager	manager		= new EntityManager(eng, this);
	public int		width, height, hexWidht, hexHeight;
	public float	stateTime	= 0;

	public World(int width, int height, int hexWidht, int hexHeight) {
		this.width = width;
		this.height = height;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;
		eng.addSystem(new RenderSystem());

		generateNewWorldType();
	}

	public void generateNewWorldType() {
		eng.removeAllEntities();
		WorldGenerator.genWorld(this, GenType.RectLife);
	}

	/**
	 * updates but also renders the world .
	 * use setRender(boolean render) to enable/disable rendering
	 * 
	 * @param delta
	 */
	public void manage( float delta ) {
		stateTime += delta;
		// stateTime %= 86;
		eng.update(delta);
	}

	public void setRender( boolean render ) {
		eng.getSystem(RenderSystem.class).render = render;
	}

	@Override
	public void dispose() {
		eng.clearPools();
	}

	Vector3	tHit	= new Vector3();

	public void hit( float x, float y ) {
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> ents = eng.getEntitiesFor(Family.getFor(CCollision.class));

		for (int i = 0; i < ents.size(); i++) {
			CCollision col = CM.Col.get(ents.get(i));
			if ( col.isInside(x, y) ) {
				CTexture tex = CM.Tex.get(ents.get(i));
				if ( tex != null ) {
					AtlasRegion hili = SF.atlas.findRegion("tile_highlight");
					if ( tex.tex.contains(hili, true) )
						tex.tex.removeValue(hili, true);
					else
						tex.tex.add(hili);

				}
				CInteract inter = CM.Inter.get(ents.get(i));
				if ( inter != null )
					inter.interact.hit(ents.get(i));

				break;
			}
		}
	}
}

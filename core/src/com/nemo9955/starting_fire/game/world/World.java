package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.RenderSystem;
import com.nemo9955.starting_fire.game.ashley.UpdateSystem;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CIHit;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.game.notifications.NotWorckBench;
import com.nemo9955.starting_fire.game.notifications.NotificationManager;
import com.nemo9955.starting_fire.game.world.WorldGenerator.GenType;
import com.nemo9955.starting_fire.storage.SF;

public class World implements Disposable {

	PooledEngine			engine		= new PooledEngine();
	public EntityManager	manager		= new EntityManager(this);
	public int				width, height, hexWidht, hexHeight;
	public float			stateTime	= 0;
	public boolean			offsetPlace	= false;

	private IntMap<Entity>	map;

	public World(int width, int height, int hexWidht, int hexHeight) {
		this.width = width;
		this.height = height;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;
		map = new IntMap<Entity>(width * height);

		engine.addSystem(new RenderSystem());
		engine.addSystem(new UpdateSystem());

		generateNewWorldType();
	}

	public void generateNewWorldType() {
		Events.clearAll();
		engine.removeAllEntities();
		map.clear();

		NotificationManager.registerNotif(new NotWorckBench());
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
		engine.update(delta);
	}

	public void setRender( boolean render ) {
		engine.getSystem(RenderSystem.class).render = render;
	}

	@Override
	public void dispose() {
		engine.clearPools();
	}

	/**
	 * 
	 * creates or returns the hexagon Entity at those coordinates
	 * 
	 * @param q
	 * @param r
	 * @return
	 */
	public Entity getHex( int q, int r ) {
		Entity entity;
		int key = width * q + r;

		if ( map.containsKey(key) ) {
			// Gdx.app.log("WorldGen", "EXISTS : " + key + " -> " + q + " " + r);
			entity = map.get(key);
		} else {
			// Gdx.app.log("WorldGen", "CREATE : " + key + " -> " + q + " " + r);
			entity = engine.createEntity();
			CWorld cw = engine.createComponent(CWorld.class);
			cw.world = this;
			entity.add(cw);
			map.put(key, entity);
		}

		return entity;
	}

	public <T extends Component> T createComponent( Class<T> componentType ) {
		return engine.createComponent(componentType);
	}

	Vector3	tHit	= new Vector3();

	public void hit( float x, float y ) {
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> ents = engine.getEntitiesFor(Family.getFor(CCollision.class));

		for (int i = 0; i < ents.size(); i++) {
			CCollision col = CM.Col.get(ents.get(i));
			if ( col.isInside(x, y) ) {

				CIHit h = CM.Inter.get(ents.get(i));
				if ( h != null )
					h.hitter.hit(ents.get(i));
				else {
					CTexture tex = CM.Tex.get(ents.get(i));
					if ( tex != null ) {
						AtlasRegion hili = SF.atlas.findRegion("tile_highlight");
						if ( tex.tex.contains(hili, true) )
							tex.tex.removeValue(hili, true);
						else
							tex.tex.add(hili);
					}
				}
				break;
			}
		}
	}
}

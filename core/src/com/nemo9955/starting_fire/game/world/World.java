package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.nemo9955.starting_fire.game.ashley.RenderSystem;
import com.nemo9955.starting_fire.game.ashley.UpdateSystem;
import com.nemo9955.starting_fire.game.ashley.components.CActor;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CHit;
import com.nemo9955.starting_fire.game.ashley.components.CHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTelegraph;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.storage.SF;

public class World implements Disposable {

	private UpdateSystem updSys = new UpdateSystem();
	private RenderSystem rendSys = new RenderSystem();

	PooledEngine engine = new PooledEngine();

	// public EntityManager manager = new EntityManager(this);

	public int maxWidth, maxHeight, hexWidht, hexHeight;
	public boolean offsetPlace1 = !false;

	private IntMap<Entity> map;

	public PlayerBuilder player = new PlayerBuilder();

	public World(int maxWidth, int maxHeight, int hexWidht, int hexHeight) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;
		map = new IntMap<Entity>(maxWidth * maxHeight);

		engine.addSystem(rendSys);
		engine.addSystem(updSys);

		generateNewWorldType();
	}

	public void generateNewWorldType() {
		Events.reset();
		engine.removeAllEntities();
		map.clear();
		SF.gameplay.stage.restart();

		// WorldGenerator.genWorld(this, GenType.HoneyComb);

		engine.addEntity(player.useElement(createEntity()));
	}

	/**
	 * 
	 * creates or returns the hexagon Entity at those coordinates
	 * 
	 * @param q
	 * @param r
	 * @return
	 */
	public Entity getHex(int q, int r) {
		Entity entity;
		int key = 1000 * q + r;

		if (map.containsKey(key)) {
			// Gdx.app.log("WorldGen", "EXISTS : " + key + " -> " + q + " " +
			// r);
			entity = map.get(key);
		} else {
			// Gdx.app.log("WorldGen", "CREATE : " + key + " -> " + q + " " +
			// r);
			entity = createEntity();
			map.put(key, entity);
		}

		return entity;
	}

	private Entity createEntity() {
		Entity entity = engine.createEntity();
		CWorld i = engine.createComponent(CWorld.class);
		i.world = this;
		// i.spot = Spot.empty;
		entity.add(i);
		return entity;
	}

	/**
	 * updates but also renders the world . use setRender(boolean render) to
	 * enable/disable rendering
	 * 
	 * @param delta
	 */
	public void manage(float delta) {
		engine.update(delta);
	}

	public void setRender(boolean render) {
		engine.getSystem(RenderSystem.class).render = render;
	}

	@Override
	public void dispose() {
		engine.clearPools();
	}

	public <T extends Component> T createComponent(Class<T> componentType) {
		return engine.createComponent(componentType);
	}

	Vector3 tHit = new Vector3();

	public void hit(float x, float y) {
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> ents = engine.getEntitiesFor(Family.one(CCollision.class).get());

		for (int i = 0; i < ents.size(); i++) {
			CCollision col = CMap.collision.get(ents.get(i));
			if (col.isInside(x, y)) {
				// System.out.println(CMap.world.get(ents.get(i)).spot.name());

				CHit h = CMap.hit.get(ents.get(i));
				if (h != null)
					h.hitter.hit(ents.get(i));
				else {
					CTexture tex = CMap.texture.get(ents.get(i));
					if (tex != null) {
						AtlasRegion hili = SF.atlas.findRegion("tile_highlight");
						if (tex.contains(hili, true))
							tex.removeValue(hili, true);
						else
							tex.add(hili);
					}
				}
				break;
			}
		}
	}

	public int addTimer(Entity entity, float time) {
		int msgType = (int) (MathUtils.random(Integer.MAX_VALUE - 1) - System.nanoTime());
		CTimer tm = createComponent(CTimer.class);
		tm.time = time;
		tm.msgType = msgType;
		entity.add(tm);
		return msgType;
	}

	public Telegraph addTelegraph1(Entity entity) {
		CTelegraph tel = createComponent(CTelegraph.class);
		// tel.compareTo(other) = teleg;
		entity.add(tel);
		return (Telegraph) tel;
	}

	public World addHit(Entity entity, IHitable interact) {
		CHit inter = createComponent(CHit.class);
		inter.hitter = interact;
		entity.add(inter);
		return this;
	}

	// public EntityManager addUpdate(Entity entity, IUpdatable upd) {
	// CUpdate update = world.createComponent(CUpdate.class);
	// update.update = upd;
	// entity.add(update);
	// return this;
	// }

	public CActor addActor(Entity entity) {
		CActor act = createComponent(CActor.class);
		entity.add(act);
		return act;
	}

	public World addAnimation(Entity entity, Animation animation) {
		CAnimation anim = createComponent(CAnimation.class);
		anim.anim = animation;
		entity.add(anim);
		return this;
	}

	public World addCollision(Entity entity, float x, float y, float width, float height) {
		CCollision col = createComponent(CCollision.class);
		col.setColide(x, y, width, height);
		entity.add(col);
		return this;
	}

	public World addTexture(Entity entity, TextureRegion region) {
		CTexture tex = entity.getComponent(CTexture.class);
		if (tex == null)
			tex = createComponent(CTexture.class);
		tex.add(region);
		entity.add(tex);
		return this;
	}

	public CPosition addPosition(Entity entity, float x, float y) {
		CPosition poz = createComponent(CPosition.class);
		poz.x = x;
		poz.y = y;
		entity.add(poz);
		return poz;
	}

	public World addCoordinatesThenPosition(Entity entity, int q, int r) {
		CCoordinate coo = createComponent(CCoordinate.class);
		coo.r = r;
		coo.q = q;
		entity.add(coo);

		float x = hexWidht * 0.75f * q;
		float y = -(hexHeight * 0.5f * (r * 2 + 1 + q));

		addPosition(entity, x, y);

		return this;
	}

}

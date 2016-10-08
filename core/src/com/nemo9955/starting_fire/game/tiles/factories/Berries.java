package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.game.ashley.components.CHit;
import com.nemo9955.starting_fire.game.ashley.components.CHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.storage.SF;

public class Berries {

	public static Array<AtlasRegion> bush = SF.atlas.findRegions("bush");
	public static Array<AtlasRegion> berry = SF.atlas.findRegions("berry");

	public static void useElement(final Entity entity) {
		CWorld in = CMap.world.get(entity);
		// if (in.spot != Spot.empty)
		// return;
		// else
		// in.spot = Spot.used;

		startGrowth(entity);

		in.world.addTexture(entity, bush.random());
		in.world.addTimer(entity, growTime());
		// manager.addUpdate(entity, update);
	}

	private static void startGrowth(final Entity entity) {
		CWorld in = CMap.world.get(entity);
		int msgType = in.world.addTimer(entity, growTime());
		MessageManager.getInstance().addListener(new Telegraph() {

			@Override
			public boolean handleMessage(Telegram msg) {
				grownBerries(entity);
				return true;
			}
		}, msgType);
	}

	private static void pickBerries(final Entity entity) {

		startGrowth(entity);
		// i.world.manager.addUpdate(entity, update);

		CTexture t = CMap.texture.get(entity);
		t.removeAll(berry, true);
		entity.remove(CHit.class);

		Events.Berries.addAmount(pickAmount());
	}

	private static void grownBerries(Entity entity) {
		CWorld in = CMap.world.get(entity);
		in.world.addTexture(entity, berry.random());
		in.world.addHit(entity, hit);

		// entity.remove(CUpdate.class);
		entity.remove(CTimer.class);
	}

	private static float growTime() {
		return 1 + MathUtils.random(2);
	}

	private static int pickAmount() {
		return 2 + MathUtils.random(5);
	}

	private static IHitable hit = new IHitable() {

		@Override
		public void hit(Entity ent) {
			pickBerries(ent);
		}
	};

	// private static IUpdatable update = new IUpdatable() {
	//
	// @Override
	// public void update(Entity entity, float delta) {
	// CTimer t = CMap.timer.get(entity);
	//
	// if (t != null)
	// if (t.time > 0)
	// t.time -= delta;
	// else
	// grownBerries(entity);
	// }
	// };
}

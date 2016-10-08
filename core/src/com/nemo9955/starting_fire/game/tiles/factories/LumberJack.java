package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nemo9955.starting_fire.game.ashley.components.CHit;
import com.nemo9955.starting_fire.game.ashley.components.CHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.game.notifications.NotLumberJack;
import com.nemo9955.starting_fire.storage.Func;
import com.nemo9955.starting_fire.storage.SF;

public class LumberJack {

	private static float timer = 5;
	private static short amount = 10;

	public static void useElement(Entity entity) {
		entity = Func.findSpot(entity);
		CWorld in = CMap.world.get(entity);

		((Actor) NotLumberJack.inst.makeBut).setUserObject(entity);

		in.world.addTexture(entity, SF.atlas.findRegion("sand"));
		in.world.addHit(entity, hit);
	}

	// private static IUpdatable update = new IUpdatable() {
	//
	// @Override
	// public void update(Entity entity, float delta) {
	// CTimer t = CMap.timer.get(entity);
	//
	// if (t != null)
	// if (t.time > 0)
	// t.time -= delta;
	// else {
	// entity.remove(CUpdate.class);
	// CMap.world.get(entity).world.manager.addHit(entity, hit);
	// }
	// }
	// };

	private static IHitable hit = new IHitable() {

		@Override
		public void hit(final Entity entity) {
			// manager.addTimer(entity, timer);
			// manager.addUpdate(entity, update);

			CWorld i = CMap.world.get(entity);
			int msgType = i.world.addTimer(entity, timer);
			MessageManager.getInstance().addListener(new Telegraph() {

				@Override
				public boolean handleMessage(Telegram msg) {
					CMap.world.get(entity).world.addHit(entity, hit);
					return true;
				}
			}, msgType);

			Events.Wood.addAmount(amount);

			entity.remove(CHit.class);
		}
	};

}

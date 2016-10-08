package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.game.notifications.NotLumberJack;
import com.nemo9955.starting_fire.game.notifications.NotWorckBench;
import com.nemo9955.starting_fire.game.notifications.NotificationManager;
import com.nemo9955.starting_fire.storage.Func;
import com.nemo9955.starting_fire.storage.SF;

public class FirePlace {

	// public static Entity fire;

	// private static TextButton entLightFire = new TextButton("Light Fire",
	// SF.skin);

	private static Animation fireAnim = new Animation(0.1f, SF.atlas.findRegions("small_fire"), PlayMode.LOOP_PINGPONG);
	private static AtlasRegion fireUnlit = SF.atlas.findRegion("small_fire_unlit");

	public static void useElement(Entity entity) {

		entity = Func.findSpot(entity);
		CWorld in = CMap.world.get(entity);

		// DefaultTileActor actor = new DefaultTileActor(entity);
		// actor.getGroup().addActor(entLightFire);
		// actor.getGroup().addListener(listener);
		// manager.addActor(entity, actor);

		in.world.addTexture(entity, fireUnlit);
		in.world.addHit(entity, hit);
		// manager.addTelegraph(telegraph);TODO
		// fire = entity;
		// centerCamera();

		SF.gameplay.stage.hudCenter.setUserObject(entity);

		NotWorckBench.inst.makeBut.setUserObject(entity);
		NotLumberJack.inst.makeBut.setUserObject(entity);

		NotificationManager.registerNotif(NotLumberJack.inst);
		NotificationManager.registerNotif(NotWorckBench.inst);
	}

	private static void lightFire(final Entity entity) {
		float fireTime = 5f;

		CTexture tex = CMap.texture.get(entity);
		CWorld i = CMap.world.get(entity);

		if (tex != null)
			if (tex.contains(fireUnlit, true))
				tex.removeValue(fireUnlit, true);

		CAnimation anim = CMap.animation.get(entity);

		if (anim != null)
			anim.anim = fireAnim;
		else {
			i.world.addAnimation(entity, fireAnim);
		}

		CTimer t = CMap.timer.get(entity);
		if (t != null)
			t.time += fireTime;
		else {
			int msgType = i.world.addTimer(entity, fireTime);
			MessageManager.getInstance().addListener(new Telegraph() {

				@Override
				public boolean handleMessage(Telegram msg) {
					extinguishFire(entity);
					return true;
				}
			}, msgType);
		}

		// i.world.manager.addUpdate(entity, update);

		Events.Fire_Lit.addOne();
	}

	private static void extinguishFire(Entity entity) {
		CTexture tex = CMap.texture.get(entity);
		tex.add(fireUnlit);
		// entity.remove(CUpdate.class);
		entity.remove(CAnimation.class);
		// entity.remove(CTimer.class);
	}

	// private static ChangeListener listener = new ChangeListener() {
	// @Override
	// public void changed(ChangeEvent event, Actor act) {
	// Group parent = act.getParent();
	// Entity entity = (Entity) parent.getUserObject();
	// if (entLightFire.isPressed())
	// lightFire(entity);
	// // actor.actor.getGroup().setVisible(false);
	// }
	// };
	private static IHitable hit = new IHitable() {

		@Override
		public void hit(Entity entity) {
			lightFire(entity);
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
	// extinguishFire(entity);
	// }
	// };

}

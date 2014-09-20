package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CInfo;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.game.stage.DefaultTileActor;
import com.nemo9955.starting_fire.storage.Func;
import com.nemo9955.starting_fire.storage.SF;

public class FireFactory {

	public static Entity		fire;

	private static TextButton	entLightFire	= new TextButton("Light Fire", SF.skin);

	private static Animation	fireAnim		= new Animation(0.1f, SF.atlas.findRegions("small_fire"), PlayMode.LOOP_PINGPONG);
	private static AtlasRegion	fireUnlit		= SF.atlas.findRegion("small_fire_unlit");

	public static void useElement( Entity entity ) {

		entity = Func.findSpot(entity, Spot.empty);

		EntityManager manager = CM.Info.get(entity).world.manager;

		DefaultTileActor actor = new DefaultTileActor(entity);
		actor.getGroup().addActor(entLightFire);
		actor.getGroup().addListener(listener);
		manager.addActor(entity, actor);

		manager.addTexture(entity, fireUnlit);
		// manager.addTelegraph(telegraph);TODO
		fire = entity;
		centerCamera();

	}

	public static void centerCamera() {
		CPosition poz = CM.Pos.get(fire);
		CInfo i = CM.Info.get(fire);
		SF.gameplay.camera.position.set(
					poz.x +
								i.world.hexWidht / 2,
					poz.y +
								i.world.hexHeight / 2, 0);
	}

	private static void lightFire( Entity entity ) {
		float fireTime = 5f;

		CTexture tex = CM.Tex.get(entity);
		CInfo i = CM.Info.get(entity);
		if ( tex != null )
			if ( tex.tex.contains(fireUnlit, true) )
				tex.tex.removeValue(fireUnlit, true);

		CAnimation anim = CM.Anim.get(entity);
		if ( anim != null )
			anim.anim = fireAnim;
		else {
			i.world.manager.addAnimation(entity, fireAnim);
		}

		CTimer t = CM.Time.get(entity);
		if ( t != null )
			t.time += fireTime;
		else
			i.world.manager.addTimer(entity, fireTime);

		i.world.manager.addUpdate(entity, update);

		Events.Fire_Lit.call();
	}

	private static void extinguishFire( Entity entity ) {
		CTexture tex = CM.Tex.get(entity);
		tex.tex.add(fireUnlit);
		entity.remove(CUpdate.class);
		entity.remove(CAnimation.class);
		entity.remove(CTimer.class);
	}

	private static ChangeListener	listener	= new ChangeListener() {

													@Override
													public void changed( ChangeEvent event, Actor act ) {
														Group parent = act.getParent();
														Entity entity = (Entity) parent.getUserObject();

														if ( entLightFire.isPressed() )
															lightFire(entity);

														// actor.actor.getGroup().setVisible(false);
													}
												};

	private static IUpdatable		update		= new IUpdatable() {

													@Override
													public void update( Entity entity, float delta ) {
														CTimer t = CM.Time.get(entity);

														if ( t != null )
															if ( t.time > 0 )
																t.time -= delta;
															else
																extinguishFire(entity);
													}
												};

	// private static Telegraph telegraph = new Telegraph() {
	//
	// @Override
	// public boolean handleMessage( Telegram msg ) {
	// System.out.println("FireFactory recived telegram : " + msg.message);
	// return false;
	// }
	// };

}

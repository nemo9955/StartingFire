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
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Event;
import com.nemo9955.starting_fire.game.events.EventManager;
import com.nemo9955.starting_fire.game.stage.DefaultTileActor;
import com.nemo9955.starting_fire.storage.SF;

public class FireFactory {

	private static TextButton	entLightFire	= new TextButton("Light Fire", SF.skin);
	private static TextButton	entMWorkB		= new TextButton("Make WorkBench", SF.skin);

	private static Animation	fireAnim		= new Animation(0.1f, SF.atlas.findRegions("small_fire"), PlayMode.LOOP_PINGPONG);
	private static AtlasRegion	fireUnlit		= SF.atlas.findRegion("small_fire_unlit");

	public static void useElement( EntityManager manager ) {

		DefaultTileActor actor = new DefaultTileActor(manager);
		actor.getGroup().addActor(entLightFire);
		actor.getGroup().addActor(entMWorkB);
		actor.getGroup().addListener(listener);
		manager.addActor(actor);

		manager.addTexture(fireUnlit);
		// manager.addUpdate(update);
		// manager.addTelegraph(telegraph);TODO
	}

	private static void lightFire( Entity entity ) {
		float fireTime = 5f;

		CTexture tex = CM.Tex.get(entity);
		CWorld w = CM.Wor.get(entity);
		if ( tex != null )
			if ( tex.tex.contains(fireUnlit, true) )
				tex.tex.removeValue(fireUnlit, true);

		CAnimation anim = CM.Anim.get(entity);
		if ( anim != null )
			anim.anim = fireAnim;
		else {
			w.world.manager.setEntity(entity).addAnimation(fireAnim);
		}

		CTimer t = CM.Time.get(entity);
		if ( t != null )
			t.time += fireTime;
		else
			w.world.manager.setEntity(entity).addTimer(fireTime);

		w.world.manager.setEntity(entity).addUpdate(update);

		EventManager.call(Event.Fire_Lit);
	}

	private static void extinguishFire( Entity entity ) {
		CTexture tex = CM.Tex.get(entity);
		tex.tex.add(fireUnlit);
		entity.remove(CIUpdate.class);
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
														else if ( entMWorkB.isPressed() ) {
															CCoordinate co = CM.Coor.get(entity);
															CWorld wo = CM.Wor.get(entity);

															EntityManager manager = wo.world.manager;
															manager.setEntity(wo.world.getHex(co.q + 2, co.r - 1));

															WorckBFactory.useElement(manager);

															parent.removeActor(entMWorkB);
														}

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

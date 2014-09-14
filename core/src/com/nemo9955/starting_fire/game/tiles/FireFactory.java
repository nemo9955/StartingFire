package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CIActor;
import com.nemo9955.starting_fire.game.ashley.components.CIHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.stage.DefaultTileActor;
import com.nemo9955.starting_fire.storage.SF;

public class FireFactory {

	private static TextButton	entLightFire	= new TextButton("Light Fire", SF.skin);
	private static TextButton	entMWorkB		= new TextButton("Make WorkBench", SF.skin);

	private static Animation	fireAnim		= new Animation(0.1f, SF.atlas.findRegions("small_fire"), PlayMode.LOOP_PINGPONG);
	private static AtlasRegion	fireUnlit		= SF.atlas.findRegion("small_fire_unlit");

	public static void useElement( EntityManager manager ) {

		DefaultTileActor actor = new DefaultTileActor(manager.getEntity());
		actor.getGroup().addActor(entLightFire);
		actor.getGroup().addActor(entMWorkB);
		actor.getGroup().addListener(listener);
		manager.addActor(actor);

		manager.addTexture(fireUnlit);
		manager.addHit(hit);
		// manager.addUpdate(update);
		// manager.addTelegraph(telegraph);TODO
	}

	private static void lightFire( Entity entity ) {
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

		w.world.manager.setEntity(entity).addTimer(5f);
		w.world.manager.setEntity(entity).addUpdate(update);

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
														Entity entity = (Entity) act.getParent().getUserObject();

														if ( entLightFire.isPressed() )
															lightFire(entity);
														else if ( entMWorkB.isPressed() ) {
															// TODO get sprite for workbench ,
														}

														CIActor actor = CM.Act.get(entity);
														actor.actor.getGroup().setVisible(false);
													}
												};
	private static IHitable			hit			= new IHitable() {

													@Override
													public void hit( Entity ent ) {
														CIActor act = CM.Act.get(ent);
														CPosition poz = CM.Pos.get(ent);
														CWorld w = CM.Wor.get(ent);
														SF.gameplay.camera.project(SF.tpC1.set(poz.x + w.world.hexWidht / 2, poz.y
																	+ w.world.hexHeight / 2,
																	0));
														act.actor.getGroup().setPosition(SF.tpC1.x, SF.tpC1.y);
														act.actor.getGroup().setVisible(!act.actor.getGroup().isVisible());

													}
												};
	private static IUpdatable		update		= new IUpdatable() {

													@Override
													public void update( Entity entity, float delta ) {
														CTimer time = CM.Time.get(entity);
														time.time -= delta;

														if ( time.time <= 0 ) {
															extinguishFire(entity);
														}
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

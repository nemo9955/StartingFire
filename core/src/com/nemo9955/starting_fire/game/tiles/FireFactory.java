package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.IInteractable;
import com.nemo9955.starting_fire.game.ashley.components.CActor;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.stage.IActable;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.CircularGroup;

public class FireFactory implements IActable, IInteractable {

	Entity						theEnt;
	CircularGroup				actor			= new CircularGroup(SF.shapeRend);

	TextButton					entLightFire	= new TextButton("Light Fire", SF.skin);

	private static Animation	fireAnim		= new Animation(0.1f, SF.atlas.findRegions("small_fire"), PlayMode.LOOP_PINGPONG);
	private static AtlasRegion	fireUnlit		= SF.atlas.findRegion("small_fire_unlit");

	{
		actor.setDraggable(false);
		actor.setVisible(false);
		actor.addActor(entLightFire);

		actor.setAsCircle(100, 30);
		actor.setActivInterval(90, 90, true, 60, false);
		actor.setModifyAlpha(false);

		actor.addListener(new ChangeListener() {

			@Override
			public void changed( ChangeEvent event, Actor actor ) {
				if ( entLightFire.isPressed() )
					lightFire();

			}

		});
	}

	private void lightFire() {
		CTexture tex = CM.Tex.get(theEnt);
		if ( tex != null )
			if ( tex.tex.contains(fireUnlit, true) )
				tex.tex.removeValue(fireUnlit, true);

		CAnimation anim = CM.Anim.get(theEnt);
		anim.anim = fireAnim;
	}

	public static void useElement( EntityManager manager ) {
		FireFactory ff = new FireFactory();
		ff.theEnt = manager.getEntity();
		SF.gameplay.stage.entActors.add(ff);

		manager.addTexture(fireUnlit);
		manager.addActor(ff);
		manager.addInteract(ff);
	}

	@Override
	public void hit( Entity ent ) {
		theEnt = ent;
		CActor act = CM.Act.get(theEnt);
		CPosition poz = CM.Pos.get(theEnt);
		CWorld w = CM.Wor.get(theEnt);
		SF.gameplay.camera.project(SF.tpC1.set(poz.x + w.world.hexWidht / 2, poz.y + w.world.hexHeight / 2, 0));
		act.actor.getActor().setPosition(SF.tpC1.x, SF.tpC1.y);
		act.actor.getActor().setVisible(!act.actor.getActor().isVisible());

	}

	@Override
	public void resize( int width, int height ) {

		CActor act = CM.Act.get(theEnt);
		CPosition poz = CM.Pos.get(theEnt);
		CWorld w = CM.Wor.get(theEnt);
		SF.gameplay.camera.project(SF.tpC1.set(poz.x + w.world.hexWidht / 2, poz.y + w.world.hexHeight / 2, 0));
		act.actor.getActor().setPosition(SF.tpC1.x, SF.tpC1.y);

		actor.setPosition(200, 200);// TODO
	}

	@Override
	public void restart() {

	}

	@Override
	public Actor getActor() {
		return actor;
	}

}

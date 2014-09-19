package com.nemo9955.starting_fire.game.stage;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CIActor;
import com.nemo9955.starting_fire.game.ashley.components.CIActor.IActable;
import com.nemo9955.starting_fire.game.ashley.components.CIHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.CircularGroup;

public class DefaultTileActor implements IActable {

	CircularGroup	actor;

	public DefaultTileActor(EntityManager manager) {
		manager.addHit(hit);
		actor = new CircularGroup(SF.shapeRend) {

			@Override
			public void setVisible( boolean visible ) {
				super.setVisible(visible);
				SF.gameplay.activateAllInputes(!visible);
			}
		};

		actor.setUserObject(manager.getEntity());
		actor.setVisible(false);
		actor.setHideAtMiss(true);

		actor.setAsCircle(100, 30);
		actor.setActivInterval(90, 270, true, 40, false);

		SF.gameplay.stage.addEntActor(this);
	}

	private static Vector3	tpC1	= new Vector3();

	@Override
	public void resize( int width, int height ) {

		CIActor act = CM.Act.get((Entity) actor.getUserObject());
		CPosition poz = CM.Pos.get((Entity) actor.getUserObject());
		CWorld w = CM.Wor.get((Entity) actor.getUserObject());
		SF.gameplay.camera.project(tpC1.set(poz.x + w.world.hexWidht / 2, poz.y + w.world.hexHeight / 2, 0));
		act.actor.getGroup().setPosition(tpC1.x, tpC1.y);

	}

	@Override
	public void restart() {
		actor.setVisible(false);
	}

	@Override
	public Group getGroup() {
		return actor;
	}

	private static IHitable	hit	= new IHitable() {

									@Override
									public void hit( Entity ent ) {
										CIActor act = CM.Act.get(ent);
										CPosition poz = CM.Pos.get(ent);
										CWorld w = CM.Wor.get(ent);
										SF.gameplay.camera.project(tpC1.set(poz.x + w.world.hexWidht / 2, poz.y + w.world.hexHeight / 2,
													0));
										act.actor.getGroup().setPosition(tpC1.x, tpC1.y);
										act.actor.getGroup().setVisible(!act.actor.getGroup().isVisible());

									}
								};
}

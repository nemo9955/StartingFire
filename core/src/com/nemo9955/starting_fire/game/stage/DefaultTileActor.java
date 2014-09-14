package com.nemo9955.starting_fire.game.stage;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.nemo9955.starting_fire.game.ashley.components.CIActor;
import com.nemo9955.starting_fire.game.ashley.components.CIActor.IActable;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.CircularGroup;

public class DefaultTileActor implements IActable {

	CircularGroup	actor;

	public DefaultTileActor(Entity entity) {
		actor = new CircularGroup(SF.shapeRend);

		actor.setUserObject(entity);
		actor.setDraggable(false);
		actor.setVisible(false);

		actor.setAsCircle(100, 30);
		actor.setActivInterval(180, 0, !true, 60, false);

		SF.gameplay.stage.addEntActor(this);
	}

	@Override
	public void resize( int width, int height ) {

		CIActor act = CM.Act.get((Entity) actor.getUserObject());
		CPosition poz = CM.Pos.get((Entity) actor.getUserObject());
		CWorld w = CM.Wor.get((Entity) actor.getUserObject());
		SF.gameplay.camera.project(SF.tpC1.set(poz.x + w.world.hexWidht / 2, poz.y + w.world.hexHeight / 2, 0));
		act.actor.getGroup().setPosition(SF.tpC1.x, SF.tpC1.y);

	}

	@Override
	public void restart() {
		actor.setVisible(false);
	}

	@Override
	public Group getGroup() {
		return actor;
	}

}

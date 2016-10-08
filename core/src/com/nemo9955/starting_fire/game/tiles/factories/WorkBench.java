package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.nemo9955.starting_fire.game.ashley.components.CActor;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.Func;
import com.nemo9955.starting_fire.storage.SF;

public class WorkBench {

	public static void useElement(Entity entity) {
		entity = Func.findSpot(entity);
		CWorld in = CMap.world.get(entity);

		// DefaultTileActor actor = new DefaultTileActor(entity);
		// actor.getGroup().addListener(listener);

		// ((Actor) NotWorckBench.inst.makeBut).setUserObject(entity);

		CActor act = in.world.addActor(entity);
		act.addActor(new TextButton("Work", SF.skin));

		in.world.addTexture(entity, SF.atlas.findRegion("workbench"));

	}

	// private static ChangeListener listener = new ChangeListener() {
	//
	// @Override
	// public void changed(ChangeEvent event, Actor act) {
	//
	// }
	// };

}

package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.stage.DefaultTileActor;
import com.nemo9955.starting_fire.storage.SF;

public class WorckBFactory {

	// private static TextButton back = new TextButton("Back", SF.skin);

	public static void useElement( EntityManager manager ) {

		DefaultTileActor actor = new DefaultTileActor(manager);
		// actor.getGroup().addActor(back);
		actor.getGroup().addListener(listener);

		manager.addActor(actor);
		manager.addTexture(SF.atlas.findRegion("sand"));
	}

	private static ChangeListener	listener	= new ChangeListener() {

													@Override
													public void changed( ChangeEvent event, Actor act ) {
														// Entity entity = (Entity) act.getParent().getUserObject();
														// Group parent = act.getParent();

													}
												};

}

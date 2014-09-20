package com.nemo9955.starting_fire.game.ashley;

import java.util.Comparator;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;

/**
 * 
 * Class used to sort Entities (world tiles) before adding them to the engine .
 * 
 * @author nemo9955
 *
 */
public class PreSorter {

	private static Array<IndexedEntity>	ents;

	/**
	 * Call this before starting to create the entities .
	 */
	public static void begin() {
		ents = new Array<IndexedEntity>(false, 5);
	}

	/**
	 * 
	 * @param ent
	 *            adds the entity to the sorting array
	 */
	public static void add( Entity ent ) {
		CPosition poz = ent.getComponent(CPosition.class);
		ents.add(new IndexedEntity(
					poz.y, ent));
	}

	/**
	 * Call this when finished adding entities to the sorter .
	 * 
	 * @param engine
	 *            the engine in which to add all the sorted entities .
	 */
	public static void end( Engine engine ) {
		ents.sort(new Comparator<IndexedEntity>() {

			@Override
			public int compare( IndexedEntity o1, IndexedEntity o2 ) {
				return (int) (o2.y - o1.y);
			}
		});

		for (IndexedEntity ine : ents)
			engine.addEntity(ine.ent);

		ents.clear();
	}

	/**
	 * 
	 * Class to hold the entity to be sorted with it's Y position for rapid access
	 * 
	 * @author nemo9955
	 *
	 */
	private static class IndexedEntity {

		float	y;
		Entity	ent;

		public IndexedEntity(float y, Entity ent) {
			this.y = y;
			this.ent = ent;
		}

	}
}

package com.nemo9955.starting_fire.game.stage;

import com.badlogic.ashley.core.Entity;

public class DefaultTileActor {

	// Table actor;

	public DefaultTileActor(Entity entity) {
		// EntityManager manager = CMap.world.get(entity).world.manager;
		// manager.addHit(entity, hit);
		// actor = new Table(SF.skin);
		// actor = new CircularGroup(SF.shapeRend) {
		//
		// @Override
		// public void setVisible(boolean visible) {
		// super.setVisible(visible);
		// SF.gameplay.activateAllInputes(!visible);
		// }
		// };

		// actor.setUserObject(entity);
		// actor.setVisible(false);
		// actor.setHideAtMiss(true);
		//
		// actor.setAsCircle(100, 30);
		// actor.setActivInterval(90, 270, true, 40, false);
		//
		// SF.gameplay.stage.addEntActor(this);
	}

	// private static Vector3 tpC1 = new Vector3();

	// @Override
	// public void resize(int maxWidth, int maxHeight) {
	// CActor act = CMap.actor.get((Entity) actor.getUserObject());
	// CPosition poz = CMap.position.get((Entity) actor.getUserObject());
	// CWorld i = CMap.world.get((Entity) actor.getUserObject());
	// SF.gameplay.camera.project(tpC1.set(poz.x + i.world.hexWidht / 2, poz.y +
	// i.world.hexHeight / 2, 0));
	// act.actor.getGroup().setPosition(tpC1.x, tpC1.y);
	// }
	//
	// @Override
	// public Group getGroup() {
	// return actor;
	// }
	//
	// private static IHitable hit = new IHitable() {
	//
	// @Override
	// public void hit(Entity ent) {
	// CActor act = CMap.actor.get(ent);
	// CPosition poz = CMap.position.get(ent);
	// CWorld i = CMap.world.get(ent);
	// SF.gameplay.camera.project(tpC1.set(poz.x + i.world.hexWidht / 2, poz.y +
	// i.world.hexHeight / 2, 0));
	// act.actor.getGroup().setPosition(tpC1.x, tpC1.y);
	// act.actor.getGroup().setVisible(!act.actor.getGroup().isVisible());
	//
	// }
	// };
}

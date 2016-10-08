package com.nemo9955.starting_fire.storage;

import com.badlogic.ashley.core.Entity;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.world.World;

public class Func {

	public static Entity findSpot( Entity entity) {

		// CCoordinate co = CMap.coordinate.get(entity);
		// CWorld inf = CMap.world.get(entity);
		//
		// if ( inf.spot == spot ) {
		// inf.spot = Spot.used;
		// return entity;
		// }
		//
		// int[] ofs = Func.getNeighbor(inf.world);
		// for (int i = 0; i < ofs.length; i += 2) {
		// Entity ent = inf.world.getHex(co.q + ofs[i], co.r + ofs[i + 1]);
		// CWorld ie = CMap.world.get(ent);
		// if ( ie.spot == spot ) {
		// ie.spot = Spot.used;
		// return ent;
		// }
		// }

		return entity;
	}

	public static void centerCamera(Entity entity) {
		CPosition poz = CMap.position.get(entity);
		CWorld i = CMap.world.get(entity);
//		System.out.println(poz);
//		System.out.println(i);
		SF.gameplay.camera.position.set(poz.x + i.world.hexWidht / 2, poz.y + i.world.hexHeight / 2, 0);
	}
	public static int[] getNeighbor( World world ) {
//		if ( world.offsetPlace )
//			return Vars.neiOfs;
		return Vars.neiAxi;
	}

}

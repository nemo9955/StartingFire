package com.nemo9955.starting_fire.storage;

import com.badlogic.ashley.core.Entity;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CInfo;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.world.World;

public class Func {

	public static Entity findSpot( Entity entity, Spot spot ) {

		CCoordinate co = CM.Coor.get(entity);
		CInfo inf = CM.Info.get(entity);

		if ( inf.spot == spot ) {
			inf.spot = Spot.used;
			return entity;
		}

		int[] ofs = Func.getNeighbor(inf.world);
		for (int i = 0; i < ofs.length; i += 2) {
			Entity ent = inf.world.getHex(co.q + ofs[i], co.r + ofs[i + 1]);
			CInfo ie = CM.Info.get(ent);
			if ( ie.spot == spot ) {
				ie.spot = Spot.used;
				return ent;
			}
		}

		return entity;
	}

	public static int[] getNeighbor( World world ) {
		if ( world.offsetPlace )
			return Vars.neiOfs;
		return Vars.neiAxi;
	}

}

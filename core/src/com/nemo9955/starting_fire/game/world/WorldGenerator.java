package com.nemo9955.starting_fire.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.nemo9955.starting_fire.game.ashley.PreSorter;
import com.nemo9955.starting_fire.game.tiles.HexBase;

public class WorldGenerator {

	private static World	world;

	static void generateNoiseWorld() {
		float chanceAllive = 0.4f;
		int birthLimit = 4;
		int deathLimit = 4;
		int steps = 1;

		boolean[][] map = new boolean[world.width][world.height];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++)
				map[i][j] = MathUtils.randomBoolean(chanceAllive) ? true : false;

		for (int step = 0; step < steps; step++) {
			boolean[][] temp = new boolean[world.width][world.height];
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
					int nbs = countAliveNeighbours(map, x, y);
					if ( map[x][y] ) {
						if ( nbs < deathLimit )
							temp[x][y] = false;
						else
							temp[x][y] = true;
					}
					else {
						if ( nbs > birthLimit )
							temp[x][y] = true;
						else
							temp[x][y] = false;
					}
				}
			}
			map = temp;
		}

		PreSorter.begin();
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++)
				if ( map[i][j] )
					PreSorter.add(world.manager.makeHex(HexBase.GRASS, i, j));
				else if ( MathUtils.randomBoolean(0.8f) )
					PreSorter.add(world.manager.makeHex(HexBase.DIRT, i, j));
				else
					PreSorter.add(world.manager.makeHex(HexBase.GRAVEL, i, j));

		PreSorter.end(world.engine);

	}

	static int countAliveNeighbours( boolean[][] map, int x, int y ) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int neighbour_x = x + i;
				int neighbour_y = y + j;
				if ( i == 0 && j == 0 ) {
				}
				else if ( neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length ) {
					count = count + 1;
				}
				else if ( map[neighbour_x][neighbour_y] ) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	static void generateNewRandWorld() {
		PreSorter.begin();
		for (int col = 0; col < world.height; col++)
			for (int row = world.width - 1; row > -1; row--)
				PreSorter.add(world.manager.makeHex(HexBase.GRASS, col, row));
		PreSorter.end(world.engine);
	}

	static void generateHoneyComb( int radius ) {
		world.offsetPlace = false;
		PreSorter.begin();
		PreSorter.add(world.manager.makeHex(HexBase.GRASS, 0, 0));
		for (int r = 0; r > -radius; r--)
			for (int q = -r - 1; q > -radius - r; q--)
				PreSorter.add(world.manager.makeHex(HexBase.GRASS, q, r));
		for (int r = 1; r < radius; r++)
			for (int q = 0; q > -radius; q--)
				PreSorter.add(world.manager.makeHex(HexBase.GRASS, q, r));
		for (int q = 1; q < radius; q++)
			for (int r = -q; r < radius - q; r++)
				PreSorter.add(world.manager.makeHex(HexBase.GRASS, q, r));
		PreSorter.end(world.engine);
	}

	static void genWorld( World world, GenType type ) {
		WorldGenerator.world = world;

		switch ( type ) {
			case HoneyComb :
				generateHoneyComb(10);
				break;
			case Random :
				generateNewRandWorld();
				break;
			case RectLife :
				generateNoiseWorld();
				break;
		}
		world = null;
	}

	public static enum GenType {
		Random, HoneyComb, RectLife;
	}

}

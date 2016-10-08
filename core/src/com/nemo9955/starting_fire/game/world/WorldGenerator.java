package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.nemo9955.starting_fire.game.ashley.PreSorter;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.tiles.TileTypes;
import com.nemo9955.starting_fire.game.tiles.factories.Decals;
import com.nemo9955.starting_fire.game.tiles.factories.FirePlace;
import com.nemo9955.starting_fire.storage.Func;

public class WorldGenerator {

	private static World world;

	public static Entity makeHex(TileTypes hex, int q, int r) {
		Entity entity = world.getHex(q, r);
		// EntityManager manager = CMap.world.get(entity).world.manager;
		// CMap.world.get(entity).hex = hex;

		world.addCoordinatesThenPosition(entity, q, r);
		world.addTexture(entity, hex.getTex());

		// float x = world.hexWidht * 0.75f * q;
		// float y;
		// if ( world.offsetPlace )
		// y = ((q & 1) == 1 ? world.hexHeight * 0.5f : 0) - (world.hexHeight *
		// r);
		// else
		// y = -(world.hexHeight * 0.5f * (r * 2 + 1 + q));

		// manager.addPosition(entity, x, y);
		CPosition cp = CMap.position.get(entity);
		world.addCollision(entity, cp.x, cp.y, world.hexWidht, world.hexHeight);

		return entity;
	}

	static void generateNoiseWorld() {
		int[] ofs = Func.getNeighbor(world);
		float chanceAllive = 0.4f;
		int birthLimit = 4;
		int deathLimit = 4;
		int steps = 1;

		boolean[][] map = new boolean[world.maxWidth][world.maxHeight];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++)
				map[i][j] = MathUtils.randomBoolean(chanceAllive) ? true : false;

		int fq = world.maxWidth / 2;
		int fr = world.maxHeight / 2;

		map[fq][fr] = false;
		for (int o = 0; o < ofs.length; o += 2)
			map[fq + ofs[o]][fr + ofs[o] + 1] = false;

		for (int step = 0; step < steps; step++) {
			boolean[][] temp = new boolean[world.maxWidth][world.maxHeight];
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
					int nbs = countAliveNeighbours(map, x, y);
					if (map[x][y]) {
						if (nbs < deathLimit)
							temp[x][y] = false;
						else
							temp[x][y] = true;
					} else {
						if (nbs > birthLimit)
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
				if (map[i][j])
					PreSorter.add(makeHex(TileTypes.Grass, i, j));
				else if (MathUtils.randomBoolean(0.8f))
					PreSorter.add(makeHex(TileTypes.Dirt, i, j));
				else
					PreSorter.add(makeHex(TileTypes.Gravel, i, j));

		PreSorter.end(world.engine);
		FirePlace.useElement(world.getHex(world.maxWidth / 2, world.maxHeight / 2));

	}

	static int countAliveNeighbours(boolean[][] map, int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int neighbour_x = x + i;
				int neighbour_y = y + j;
				if (i == 0 && j == 0) {
				} else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length
						|| neighbour_y >= map[0].length) {
					count = count + 1;
				} else if (map[neighbour_x][neighbour_y]) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	static void generateNewRandWorld() {
		PreSorter.begin();
		for (int col = 0; col < world.maxHeight; col++)
			for (int row = world.maxWidth - 1; row > -1; row--)
				PreSorter.add(makeHex(TileTypes.Grass, col, row));
		PreSorter.end(world.engine);
	}

	static void generateHoneyComb(int radius) {
		// world.offsetPlace = false;
		PreSorter.begin();
		PreSorter.add(makeHex(TileTypes.Grass, 0, 0));
		for (int r = 0; r > -radius; r--)
			for (int q = -r - 1; q > -radius - r; q--)
				PreSorter.add(makeHex(TileTypes.Grass, q, r));
		for (int r = 1; r < radius; r++)
			for (int q = 0; q > -radius; q--)
				PreSorter.add(makeHex(TileTypes.Grass, q, r));
		for (int q = 1; q < radius; q++)
			for (int r = -q; r < radius - q; r++)
				PreSorter.add(makeHex(TileTypes.Grass, q, r));
		PreSorter.end(world.engine);

		FirePlace.useElement(world.getHex(0, 0));
	}

	static void genWorld(World world, GenType type) {
		WorldGenerator.world = world;

		switch (type) {
		case HoneyComb:
			generateHoneyComb(10);
			break;
		case Random:
			generateNewRandWorld();
			break;
		case RectLife:
			generateNoiseWorld();
			break;
		}

		addDetails();

	}

	@SuppressWarnings("unchecked")
	private static void addDetails() {
		ImmutableArray<Entity> entities = world.engine.getEntitiesFor(Family.one(CWorld.class).get());

		// for (Entity ent : entities)
		// if (CMap.world.get(ent).hex == TileTypes.Grass &&
		// MathUtils.randomBoolean(0.13f))
		// Trees.useElement(ent);
		//
		// for (Entity ent : entities)
		// if (CMap.world.get(ent).hex == TileTypes.Grass &&
		// MathUtils.randomBoolean(0.06f))
		// Berries.useElement(ent);

		for (Entity ent : entities)
			Decals.useRandomElement(ent);

		// FirePlace.useElement(world.getHex(world.width / 2, world.height /
		// 2));
	}

	public static enum GenType {
		Random, HoneyComb, RectLife;
	}

}

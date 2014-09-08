package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.nemo9955.starting_fire.game.Hex;
import com.nemo9955.starting_fire.game.ashley.PreSorter;
import com.nemo9955.starting_fire.game.ashley.RenderSystem;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.CM;
import com.nemo9955.starting_fire.storage.SF;

public class HexWorld implements Disposable {

	private PooledEngine	eng			= new PooledEngine();
	public int				width, height, hexWidht, hexHeight;
	public float			stateTime	= 0;

	public HexWorld(int width, int height, int hexWidht, int hexHeight) {
		this.width = width;
		this.height = height;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;
		eng.addSystem(new RenderSystem());

		generateNewWorldType();
	}

	public void generateNewWorldType() {
		eng.removeAllEntities();

		switch ( 2 ) {
			case 0 :
				generateHoneyComb(10);
				break;
			case 1 :
				generateNewRandWorld();
				break;
			case 2 :
				generateNoiseWorld();
				break;
		}
	}

	private void generateNoiseWorld() {
		float chanceAllive = 0.4f;
		int birthLimit = 5;
		int deathLimit = 4;
		int steps = 3;

		boolean[][] map = new boolean[width][height];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++)
				map[i][j] = MathUtils.randomBoolean(chanceAllive) ? true : false;

		for (int step = 0; step < steps; step++) {
			boolean[][] temp = new boolean[width][height];
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
					PreSorter.add(makeRandCell(Hex.GRASS, i, j, true));
				else if ( MathUtils.randomBoolean(0.8f) )
					PreSorter.add(makeRandCell(Hex.DIRT, i, j, true));
				else
					PreSorter.add(makeRandCell(Hex.GRAVEL, i, j, true));

		PreSorter.end(eng);

	}

	private int countAliveNeighbours( boolean[][] map, int x, int y ) {
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

	private void generateNewRandWorld() {
		PreSorter.begin();
		for (int col = 0; col < height; col++)
			for (int row = width - 1; row > -1; row--)
				PreSorter.add(makeRandCell(col, row, false));
		PreSorter.end(eng);
	}

	private void generateHoneyComb( int radius ) {
		PreSorter.begin();
		PreSorter.add(makeRandCell(0, 0, false));
		for (int r = 0; r > -radius; r--)
			for (int q = -r - 1; q > -radius - r; q--)
				PreSorter.add(makeRandCell(q, r, false));
		for (int r = 1; r < radius; r++)
			for (int q = 0; q > -radius; q--)
				PreSorter.add(makeRandCell(q, r, false));
		for (int q = 1; q < radius; q++)
			for (int r = -q; r < radius - q; r++)
				PreSorter.add(makeRandCell(q, r, false));
		PreSorter.end(eng);
	}

	private Entity makeRandCell( int q, int r, boolean offset ) {
		return makeRandCell(Hex.GRASS, q, r, offset);
	}

	Animation	fireAnim	= new Animation(0.1f, SF.atlas.findRegions("small_fire"), PlayMode.LOOP_PINGPONG);

	private Entity makeRandCell( Hex hex, int q, int r, boolean offset ) {
		Entity ent = createEntity();
		addCoordinates(ent, q, r);

		// if ( MathUtils.randomBoolean(0.75f) )
		addTexture(ent, hex.getTex());
		// else if ( MathUtils.randomBoolean(0.8f) )
		// addTexture(ent, Hex.DIRT.getTex());
		// else
		// addTexture(ent, Hex.GRAVEL.getTex());

		float x = hexWidht * 0.75f * q;
		float y;
		if ( offset )
			y = ((q & 1) == 1 ? hexHeight * 0.5f : 0) - (hexHeight * r);
		else
			y = -(hexHeight * 0.5f * (r * 2 + 1 + q));

		addPosition(ent, x, y);
		addCollision(ent, x, y, hexWidht, hexHeight);

		if ( q == 0 && r == 0 )
			addAnimation(ent, fireAnim);

		return ent;
	}

	public Entity addAnimation( Entity ent, Animation animation ) {
		CAnimation anim = eng.createComponent(CAnimation.class);
		anim.anim = animation;
		ent.add(anim);
		return ent;
	}

	public Entity addCollision( Entity ent, float x, float y, float width, float height ) {
		CCollision col = eng.createComponent(CCollision.class);
		col.setColide(x, y, width, height);
		ent.add(col);
		return ent;
	}

	public Entity createEntity() {
		Entity ent = eng.createEntity();
		CWorld cw = eng.createComponent(CWorld.class);
		cw.world = this;
		ent.add(cw);
		return ent;
	}

	public Entity addTexture( Entity ent, TextureRegion region ) {
		CTexture tex = ent.getComponent(CTexture.class);
		if ( tex == null )
			tex = eng.createComponent(CTexture.class);
		tex.tex.add(region);
		ent.add(tex);
		return ent;
	}

	public Entity addPosition( Entity ent, float x, float y ) {
		CPosition poz = eng.createComponent(CPosition.class);
		poz.x = x;
		poz.y = y;
		ent.add(poz);
		return ent;
	}

	public Entity addCoordinates( Entity ent, int q, int r ) {
		CCoordinate coo = eng.createComponent(CCoordinate.class);
		coo.r = r;
		coo.q = q;
		ent.add(coo);
		return ent;
	}

	public Entity makeHex( int r, int q ) {

		return null;
	}

	/**
	 * updates but also renders the world .
	 * use setRender(boolean render) to enable/disable rendering
	 * 
	 * @param delta
	 */
	public void manage( float delta ) {
		stateTime += delta;
		// stateTime %= 86;
		eng.update(delta);
	}

	public void setRender( boolean render ) {
		eng.getSystem(RenderSystem.class).render = render;
	}

	@Override
	public void dispose() {
		eng.clearPools();
	}

	public void hit( float x, float y ) {
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> ents = eng.getEntitiesFor(Family.getFor(CCollision.class));

		for (int i = 0; i < ents.size(); i++) {
			CCollision col = CM.Col.get(ents.get(i));
			if ( col.isInside(x, y) ) {
				CTexture tex = CM.Tex.get(ents.get(i));
				if ( tex == null )
					continue;
				AtlasRegion hili = SF.atlas.findRegion("sand");
				if ( tex.tex.contains(hili, true) ) {
					tex.tex.removeValue(hili, true);
				} else {
					tex.tex.add(hili);
				}
				break;
			}
		}
	}
}

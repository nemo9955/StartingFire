package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.nemo9955.starting_fire.game.Hex;
import com.nemo9955.starting_fire.game.ashley.RenderSystem;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.Assets;

public class HexWorld implements Disposable {

	private PooledEngine	eng	= new PooledEngine();
	public int				width, height, hexWidht, hexHeight;
	public float			scaling	= 0.7f, stateTime = 0;

	public HexWorld(int width, int height, int hexWidht, int hexHeight) {
		this.width = width;
		this.height = height;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;
		eng.addSystem(new RenderSystem());
		// generateNewWorld();
		generateHoneyComb(4);

	}

	public HexWorld(int width, int height, int hexWidht, int hexHeight, float scaling) {
		this.width = width;
		this.height = height;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;
		this.scaling = scaling;
		eng.addSystem(new RenderSystem());
		generateNewWorld();
	}

	private void generateHoneyComb( int radius ) {
		TextureAtlas ta = Assets.HEXAS.asset(TextureAtlas.class);

		makeRandCell(ta, 0, 0);
		for (int r = 0; r > -radius; r--)
			for (int q = -r - 1; q > -radius - r; q--)
				makeRandCell(ta, q, r);

		for (int r = 1; r < radius; r++)
			for (int q = 0; q > -radius; q--)
				makeRandCell(ta, q, r);

		for (int q = 1; q < radius; q++)
			for (int r = -q; r < radius - q; r++)
				makeRandCell(ta, q, r);

	}

	private void makeRandCell( TextureAtlas ta, int q, int r ) {
		Entity ent = createEntity();
		addCoordinates(ent, q, r);

		addPosition(ent, hexWidht * scaling * 0.75f * q,
					-(hexHeight * 0.5f * scaling * (r * 2 + 1 + q)));

		if ( q == 2 && r == -2 ) {
			addAnimation(ent, new Animation(0.1f, Assets.HEXAS.asset(TextureAtlas.class).findRegions("small_fire"),
						PlayMode.LOOP_PINGPONG));
		}

		addTexture(ent, ta.findRegion(Hex.getRandomValue().getName()));

		if ( q == 0 && r == 0 )
			addTexture(ent, Assets.HEXAS.asset(TextureAtlas.class).findRegion("small_fire_unlit"));

		eng.addEntity(ent);// TODO sort all the tiles , then add them to the engine !!
	}

	private void generateNewWorld() {
		TextureAtlas ta = Assets.HEXAS.asset(TextureAtlas.class);

		for (int col = 0; col < height; col++)
			for (int row = width - 1; row > -1; row--) {

				makeRandCell(ta, col, row);
			}
	}

	public Entity addAnimation( Entity ent, Animation animation ) {
		CAnimation anim = eng.createComponent(CAnimation.class);
		anim.anim = animation;
		ent.add(anim);
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

}

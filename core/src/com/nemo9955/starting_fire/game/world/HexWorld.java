package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.nemo9955.starting_fire.game.Hex;
import com.nemo9955.starting_fire.game.ashley.CAlignment;
import com.nemo9955.starting_fire.game.ashley.CAnimation;
import com.nemo9955.starting_fire.game.ashley.CPosition;
import com.nemo9955.starting_fire.game.ashley.CTexture;
import com.nemo9955.starting_fire.game.ashley.CWorld;
import com.nemo9955.starting_fire.game.ashley.RenderSystem;
import com.nemo9955.starting_fire.storage.Assets;

public class HexWorld implements Disposable {

	private PooledEngine	eng	= new PooledEngine();
	public int				width, height, hexWidht, hexHeight;
	public float			scaling	= 1, stateTime = 0;

	public HexWorld(int width, int height, int hexWidht, int hexHeight) {
		this.width = width;
		this.height = height;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;
		eng.addSystem(new RenderSystem());
		generateNewWorld();

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

	private void generateNewWorld() {
		TextureAtlas ta = Assets.HEXAS.asset(TextureAtlas.class);

		for (int col = 0; col < height; col++)
			for (int row = width - 1; row > -1; row--) {
				Entity ent = eng.createEntity();

				CAlignment al = eng.createComponent(CAlignment.class);
				al.col = col;
				al.row = row;
				ent.add(al);

				CPosition poz = eng.createComponent(CPosition.class);
				poz.x = (int) (hexWidht * scaling * 0.75f * col);
				poz.y = (int) ((col % 2 == 1 ? 0 : hexHeight * scaling * 0.5f) + (hexHeight * scaling * row));
				ent.add(poz);

				CTexture tex = eng.createComponent(CTexture.class);
				tex.tex.add(new TextureRegion(ta.findRegion(Hex.getRandomValue().getName())));
				ent.add(tex);

				CWorld cw = eng.createComponent(CWorld.class);
				cw.world = this;
				ent.add(cw);

				if ( row == col && row == 1 ) {
					CAnimation anim = eng.createComponent(CAnimation.class);
					anim.anim = new Animation(0.1f, Assets.HEXAS.asset(TextureAtlas.class).findRegions("small_fire"),
								PlayMode.LOOP_PINGPONG);
					ent.add(anim);
				}
				if ( col == 1 && row == 2 ) {
					tex.tex.add(Assets.HEXAS.asset(TextureAtlas.class).findRegion("small_fire_unlit"));
				}

				eng.addEntity(ent);
			}
	}

	/**
	 * updates but also renders the world .
	 * 
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

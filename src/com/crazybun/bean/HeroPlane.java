package com.crazybun.bean;

import com.crazybun.utils.Setting;

/**
 * 英雄飞机
 * 
 * @author CrazyBun
 */
public class HeroPlane extends FlyItems {
	/**
	 * 生命
	 */
	private int life;
	private int doubleFire;
	private boolean doubleFireSpeed;
	private int planeImage = 0;

	public HeroPlane() {
		image = Setting.heroPlane1;
		width = image.getWidth(null) / 2;
		height = image.getHeight(null) / 2;
		x = Setting.HERO_INIT_X - width / 2;
		y = Setting.HERO_INIT_Y;
		life = Setting.HERO_INIT_LIFE;
		doubleFire = Setting.HERO_INIT_DOUBLE;
		doubleFireSpeed = Setting.HERO_INIT_FIRE_SPEED;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getDoubleFire() {
		return doubleFire;
	}

	public void setDoubleFire(int doubleFire) {
		this.doubleFire = doubleFire;
	}

	public boolean isDoubleFireSpeed() {
		return doubleFireSpeed;
	}

	public void setDoubleFireSpeed(boolean doubleFireSpeed) {
		this.doubleFireSpeed = doubleFireSpeed;
	}

	@Override
	public boolean outOfBound() {
		return false;
	}

	@Override
	public void move() {
		image = Setting.heros[planeImage++ % 2];
	}

	public void moveTo(int x, int y) {
		this.x = x - width / 2;
		this.y = y - height / 2;
//		this.x = (x + this.x -width/2) / 2;
//		this.y = (y + this.y - height/2) / 2;
	}

	/**
	 * 发射子弹 默认一次发射一发子弹，若为双倍攻击状态则发射两发子弹
	 * 
	 * @return
	 */
	public Bullet[] shootBullet() {
		/**
		 * 子弹相对于飞机的偏移量
		 */
		int offset = this.height / 2;
		Bullet[] bs;

		// 判断是否有双倍火力，如果有双倍火力，优先双倍子弹
		if (this.doubleFire > 0) {
			bs = new Bullet[2];
			// 发射两颗子弹
			Bullet b1 = doubleFireSpeed ? new Bullet(x - width, this.y - offset) : new Bullet(x - width*11/16 + width/2, this.y, true);
			Bullet b2 = doubleFireSpeed ? new Bullet(x + width, this.y - offset) : new Bullet(x + width*11/16 + width/2, this.y, true);
			bs[0] = b1;
			bs[1] = b2;

			this.doubleFire -= 2;
		} else {
			bs = new Bullet[1];
			// 发射一颗子弹
			Bullet b = doubleFireSpeed ? new Bullet(x, this.y - offset) : new Bullet(x + width/2, this.y - offset, true);
			bs[0] = b;
		}

		return bs;
	}

	public void addLife(int addNum) {
		this.life += addNum;
	}

	public void subLife() {
		this.life--;
	}
}

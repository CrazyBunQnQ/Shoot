package com.crazybun.bean;

import com.crazybun.utils.Setting;

/**
 * 子弹类
 * 
 * @author CrazyBun
 */
public class Bullet extends FlyObjects {
	/**
	 * 是否来自英雄
	 */
	private boolean byHero;
	/**
	 * 子弹的伤害值
	 */
	public int damage = 1;

	/**
	 * 子弹
	 * 
	 * @param x
	 *            初始x坐标
	 * @param y
	 *            初始y坐标
	 * @param byHero
	 *            是否为英雄飞机的子弹
	 */
	public Bullet(int x, int y, boolean byHero) {
		this.byHero = byHero;
		image = byHero ? Setting.heroBullet : Setting.enemyBullet;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x - width / 2;
		this.y = y;
		speed = byHero ? Setting.SPEED_HERO_BULLET_MOVE : Setting.SPEED_ENEMY_BULLET;
		damage = 1;
	}

	/**
	 * 双倍移动速度的子弹
	 * 
	 * @param x
	 *            初始x坐标
	 * @param y
	 *            初始y坐标
	 */
	public Bullet(int x, int y) {
		// TODO 为何不可以这样调用 Bullet(x, y, true);
		this.byHero = true;
		image = Setting.heroBullet;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
		speed = Setting.SPEED_HERO_BULLET_MOVE * 2;
	}

	/**
	 * 是否是英雄飞机的子弹
	 * 
	 * @return boolean
	 */
	public boolean isHero() {
		return byHero;
	}

	/**
	 * 子弹移动方式
	 */
	@Override
	public void move() {
		if (byHero) {
			y -= speed;
		} else {
			y += speed;
		}
	}
}

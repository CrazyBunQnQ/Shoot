package com.crazybun.bean;

import com.crazybun.utils.Setting;

/**
 * 子弹类
 * 
 * @author CrazyBun
 */
public class Bullet extends FlyItems {
	private boolean isHero;

	/**
	 * 子弹
	 * 
	 * @param x
	 *            初始x坐标
	 * @param y
	 *            初始y坐标
	 * @param isHero
	 *            是否为英雄飞机的子弹
	 */
	public Bullet(int x, int y, boolean isHero) {
		this.isHero = isHero;
		image = isHero ? Setting.heroBullet : Setting.enemyBullet;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
		speed = isHero ? Setting.SPEED_HERO_BULLET : Setting.SPEED_ENEMY_BULLET;
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
		this.isHero = true;
		image = Setting.heroBullet;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
		speed = Setting.SPEED_HERO_BULLET * 2;
	}

	public boolean isHero() {
		return isHero;
	}

	/**
	 * 子弹移动
	 */
	@Override
	public void move() {
		if (isHero) {
			y -= speed;
		} else {
			y += speed;
		}
	}
}

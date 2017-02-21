package com.crazybun.bean;

import com.crazybun.utils.Setting;

/**
 * 敌方飞机类
 * 
 * @author CrazyBun
 */
public class EnemyPlane extends FlyItems implements Enemy {
	/**
	 * 敌方飞机类型，是否是强力飞机
	 */
	private boolean powerfulPlane;
	/**
	 * 敌方飞机的分数
	 */
	private int score;
	/**
	 * 敌方飞机移动方向，是否返回
	 */
	private boolean backX;

	/**
	 * 敌方飞机的生命值
	 */
	private int life;

	/**
	 * 敌方飞机
	 * 
	 * @param hasBuff
	 *            飞机是否包含buff
	 */
	public EnemyPlane(boolean hasBuff) {
		this.powerfulPlane = hasBuff;
		image = hasBuff ? Setting.awardPlane : Setting.enemyPlane1;
		width = image.getWidth(null);
		height = image.getHeight(null);
		x = Setting.RND.nextInt(Setting.FRAME_WIDTH - width);
		y = -height;
		speed = hasBuff ? Setting.SPEED_AWARDPLANE : Setting.SPEED_ENEMYPLANE;
		score = hasBuff ? Enemy.ENEMY_HIGH_SCORE : Enemy.ENEMY_NORMAL_SCORE;
		this.backX = Math.random() < 0.5f ? true : false;
	}

	/**
	 * 敌方飞机的移动方式
	 */
	@Override
	public void move() {
		if (powerfulPlane) {
			x = backX ? x - speed : x + speed;
			// } else {
			// image = Setting.enemies[planeImage++ % 2];
		}
		y += speed;
	}

	/**
	 * 重写越界检测
	 */
	@Override
	public boolean outOfBound() {
		backX = x < 0 ? false : (x > Setting.FRAME_WIDTH - width ? true : backX);
		return super.outOfBound();
	}

	/**
	 * 敌机发射子弹
	 * 
	 * @return Bullet
	 */
	public Bullet shootBullet() {
		return new Bullet(x + width / 2, this.y + height / 2, false);
	}

	/**
	 * 获取分数
	 * 
	 * @return int
	 */
	public int getScore() {
		return score;
	}

	/**
	 * 是否为强力飞机
	 * 
	 * @return boolean
	 */
	public boolean isPowerfulPlane() {
		return powerfulPlane;
	}
}

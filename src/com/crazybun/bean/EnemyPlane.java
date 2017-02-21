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
	private boolean backX;
	// private int planeImage = 0;

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

	@Override
	public void move() {
		if (powerfulPlane) {
			x = backX ? x - speed : x + speed;
			// } else {
			// image = Setting.enemies[planeImage++ % 2];
		}
		y += speed;
	}

	@Override
	public boolean outOfBound() {
		backX = x < 0 ? false : (x > Setting.FRAME_WIDTH - width ? true : backX);
		return super.outOfBound();
	}

	/**
	 * 敌机发射子弹
	 * 
	 * @return
	 */
	public Bullet shootBullet() {
		return new Bullet(x + width / 2, this.y + height / 2, false);
	}

	public int getScore() {
		return score;
	}

	public boolean isPowerfulPlane() {
		return powerfulPlane;
	}
}

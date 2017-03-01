package com.crazybun.logic;

import java.util.Arrays;

import com.crazybun.bean.Bullet;
import com.crazybun.bean.EnemyPlane;
import com.crazybun.view.MainPanel;

public class ActionLogic {
	/**
	 * 物品移动 包括英雄飞机，敌方飞机和所有子弹的移动
	 */
	public static void move() {
		MainPanel.hero.move();
		for (int i = 0; i < MainPanel.enemyPlanes.length; i++) {
			MainPanel.enemyPlanes[i].move();
		}
		for (int i = 0; i < MainPanel.bullets.length; i++) {
			MainPanel.bullets[i].move();
		}
		for (int i = 0; i < MainPanel.buffs.length; i++) {
			MainPanel.buffs[i].move();
		}
	}

	/**
	 * 子弹命中事件
	 */
	public static void hitByBullet() {
		Bullet[] bulletLives = new Bullet[MainPanel.bullets.length];
		int index = 0;
		for (int i = 0; i < MainPanel.bullets.length; i++) {
			Bullet bullet = MainPanel.bullets[i];
			if (bullet != null && !HitLogic.isHit(bullet, bullet.isHero())) {
				bulletLives[index++] = bullet;
			}
		}
		MainPanel.bullets = Arrays.copyOf(bulletLives, index);
	}
	
	/**
	 * 删除越界的物品 包括越界的子弹和飞机
	 */
	public static void deleteItems() {
		// 删除越界的飞机
		EnemyPlane[] fiLives = new EnemyPlane[MainPanel.enemyPlanes.length];
		int index = 0;
		for (int i = 0; i < MainPanel.enemyPlanes.length; i++) {
			EnemyPlane fi = MainPanel.enemyPlanes[i];
			if (!fi.outOfBound()) {
				fiLives[index++] = fi;
			}
		}
		MainPanel.enemyPlanes = Arrays.copyOf(fiLives, index);

		// 删除越界的子弹
		Bullet[] buttleLives = new Bullet[MainPanel.bullets.length];
		index = 0;
		for (int i = 0; i < MainPanel.bullets.length; i++) {
			Bullet b = MainPanel.bullets[i];
			if (!b.outOfBound()) {
				buttleLives[index++] = b;
			}
		}
		MainPanel.bullets = Arrays.copyOf(buttleLives, index);
		for (int i = 0; i < MainPanel.buffs.length; i++) {
			MainPanel.buffs[i].outOfBound();
		}
	}
	
	/**
	 * 删除数组中的飞机
	 * 
	 * @param i
	 *            数组下标
	 */
	public static void deleteEnemyPlanes(int i) {
		// TODO 添加死亡动画
		MainPanel.score += MainPanel.enemyPlanes[i].getScore();
		MainPanel.enemyPlanes[i] = MainPanel.enemyPlanes[MainPanel.enemyPlanes.length - 1];
		MainPanel.enemyPlanes = Arrays.copyOf(MainPanel.enemyPlanes, MainPanel.enemyPlanes.length - 1);
	}
}

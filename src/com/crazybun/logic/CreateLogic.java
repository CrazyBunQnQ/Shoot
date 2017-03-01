package com.crazybun.logic;

import java.util.Arrays;

import com.crazybun.bean.Buff;
import com.crazybun.bean.Bullet;
import com.crazybun.bean.EnemyPlane;
import com.crazybun.utils.Setting;
import com.crazybun.view.MainPanel;

public class CreateLogic {
	/**
	 * 生成敌方飞机
	 */
	public static void createEnemyPlanes() {
		if (MainPanel.planeIndex++ % 30 != 0) {// 每30帧生成一个敌方飞机
			return;
		}
		EnemyPlane fi;
		int i = ((int) (MainPanel.score / Setting.INTERVAL)) + 1;
		if (MainPanel.score > Setting.INTERVAL && i - 1 >= MainPanel.difficulty) {// Boss飞机
			fi = new EnemyPlane(3, i * 4);
			MainPanel.difficulty++;
		} else {
			double rnd = Math.random();
			if (rnd < 0.05) {// 奖励飞机
				fi = new EnemyPlane(0, 3);
			} else if (rnd > 0.75) {// 中型飞机
				fi = new EnemyPlane(2, i * 2);
			} else {// 小型飞机
				fi = new EnemyPlane(1, i);
			}
		}

		MainPanel.enemyPlanes = Arrays.copyOf(MainPanel.enemyPlanes, MainPanel.enemyPlanes.length + 1);
		MainPanel.enemyPlanes[MainPanel.enemyPlanes.length - 1] = fi;
	}

	/**
	 * 生成英雄飞机的子弹和敌方飞机的子弹
	 */
	public static void createBullets() {
		MainPanel.heroBulletCreateSpeed = MainPanel.hero.getDoubleFireSpeed() > 0 ? Setting.SPEED_HERO_BULLET_CREATE / 2
				: Setting.SPEED_HERO_BULLET_CREATE;
		if (MainPanel.bulletIndex++ % MainPanel.heroBulletCreateSpeed == 0) {
			Bullet[] bs = MainPanel.hero.shootBullet();
			MainPanel.bullets = Arrays.copyOf(MainPanel.bullets, MainPanel.bullets.length + bs.length);
			System.arraycopy(bs, 0, MainPanel.bullets, MainPanel.bullets.length - bs.length, bs.length);
		}

		int index = 0;
		if ((MainPanel.bulletIndex - 1) % Setting.SPEED_ENEMY_BULLET_CREATE == 0
				|| (MainPanel.bulletIndex - 1) % Setting.SPEED_ENEMY_BULLET_CREATE == 10) {
			Bullet[] bs = new Bullet[MainPanel.enemyPlanes.length];
			for (int i = 0; i < MainPanel.enemyPlanes.length; i++) {
				if (MainPanel.enemyPlanes[i].getPlaneType() == 3) {
					bs[index] = MainPanel.enemyPlanes[i].shootBullet();
					index++;
				}
				if ((MainPanel.bulletIndex - 1) % (Setting.SPEED_ENEMY_BULLET_CREATE * 2) == 0
						&& MainPanel.enemyPlanes[i].getPlaneType() == 2) {
					bs[index] = MainPanel.enemyPlanes[i].shootBullet();
					index++;
				}
			}
			if (index > 0) {
				MainPanel.bullets = Arrays.copyOf(MainPanel.bullets, MainPanel.bullets.length + index);
				System.arraycopy(bs, 0, MainPanel.bullets, MainPanel.bullets.length - index, index);
			}
		}
	}

	/**
	 * 在指定坐标生成buff
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public static void createBuff(int x, int y) {
		Buff buff = new Buff(x, y);
		MainPanel.buffs = Arrays.copyOf(MainPanel.buffs, MainPanel.buffs.length + 1);
		MainPanel.buffs[MainPanel.buffs.length - 1] = buff;
	}

	/**
	 * 在指定矩形范围的中心生成 buff
	 * 
	 * @param top
	 *            左上角y坐标
	 * @param bottom
	 *            右下角y坐标
	 * @param left
	 *            左上角x坐标
	 * @param right
	 *            右下角x
	 */
	public static void createBuff(int top, int bottom, int left, int right) {
		createBuff((left + right) / 2, (top + bottom) / 2);
	}
}

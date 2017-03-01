package com.crazybun.logic;

import java.util.Arrays;

import com.crazybun.bean.Buff;
import com.crazybun.bean.Bullet;
import com.crazybun.bean.EnemyPlane;
import com.crazybun.utils.Setting;
import com.crazybun.view.MainPanel;

public class HitLogic {
	/**
	 * 子弹是否命中 包括敌方子弹是否命中英雄飞机以及英雄飞机的子弹是否命中敌方飞机
	 * 
	 * @param bullet
	 *            子弹
	 * @param isHero
	 *            是否英雄飞机的子弹
	 * @return 是否命中
	 */
	public static boolean isHit(Bullet bullet, boolean isHero) {
		EnemyPlane ep = null;
		boolean isHit = false;
		if (isHero) {
			goOut: for (int i = 0; i < MainPanel.enemyPlanes.length; i++) {
				ep = MainPanel.enemyPlanes[i];
				isHit = bullet.collisionDetection(ep);
				if (isHit) {
					ep.subLife(bullet.damage);
					if (ep.getLife() <= 0) {
						if (ep.getPlaneType() == 0) {// 击杀奖励飞机生成buff
							CreateLogic.createBuff(ep.getTop(), ep.getBottom(), ep.getLeft(), ep.getRight());
						} else if (ep.getPlaneType() == 3) {// 击杀boss增加一条命
							MainPanel.hero.addLife(1);
						}
						ActionLogic.deleteEnemyPlanes(i);
					}
					break goOut;
				}
			}
		} else {
			isHit = bullet.collisionDetection(MainPanel.hero);
			if (isHit) {
				MainPanel.hero.subLife(1);
			}
		}
		return isHit;
	}

	/**
	 * 英雄飞机撞击敌方飞机事件
	 */
	public static void heroHitEnemyPlane() {
		for (int i = 0; i < MainPanel.enemyPlanes.length; i++) {
			EnemyPlane ep = MainPanel.enemyPlanes[i];
			if (ep.collisionDetection(MainPanel.hero)) {
				MainPanel.hero.subLife(3);
				// 将被撞的飞机抹除
				ActionLogic.deleteEnemyPlanes(i);
			}
		}
	}

	/**
	 * 获取buff事件,英雄撞到buff则获得增益
	 */
	public static void getBuff() {
		for (int i = 0; i < MainPanel.buffs.length; i++) {
			Buff buff = MainPanel.buffs[i];
			if (buff.collisionDetection(MainPanel.hero)) {
				switch (buff.getType()) {
				case 0:
					MainPanel.hero.addDoubleFire(Setting.HERO_INIT_DOUBLE);
					break;
				case 1:
					MainPanel.hero.addDoubleFireSpeed(Setting.HERO_INIT_FIRE_SPEED);
					break;
				case 2:
					MainPanel.hero.addLife(1);
					break;
				default:
					break;
				}
				MainPanel.buffs[i] = MainPanel.buffs[MainPanel.buffs.length - 1];
				MainPanel.buffs = Arrays.copyOf(MainPanel.buffs, MainPanel.buffs.length - 1);
			}
		}
	}
}

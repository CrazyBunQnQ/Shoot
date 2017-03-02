package com.crazybun.bean;

import java.awt.Image;

import com.crazybun.utils.Setting;

/**
 * 英雄飞机
 * 
 * @author CrazyBun
 */
public class HeroPlane extends FlyItems {
	private int doubleFire;
	private int doubleFireSpeed;
	private int planeImage = 0;
	/**
	 * 死亡图片数组
	 */
	private Image[] deathImages;
	/**
	 * 资源图片数量
	 */
	private int imageNum;

	/**
	 * 英雄飞机的构造方法
	 */
	public HeroPlane() {
		image = Setting.heroPlane1;
		width = image.getWidth(null) / 4;
		height = image.getHeight(null) / 4;
		x = Setting.HERO_INIT_X - image.getWidth(null) / 2;
		y = Setting.HERO_INIT_Y - image.getHeight(null) / 2;
		life = Setting.HERO_INIT_LIFE;
		doubleFire = 0;
		doubleFireSpeed = 0;
		deathImages = Setting.heros_death;
		imageNum = deathImages.length - 1;
		this.death = false;
		this.deathIndex = 3;
	}

	/**
	 * 获取英雄飞机双倍火力时间
	 * 
	 * @return int
	 */
	public int getDoubleFire() {
		return doubleFire;
	}

	/**
	 * 设置英雄飞机双倍火力时间
	 * 
	 * @param doubleFire
	 *            想要设置的时间，整数类型
	 */
	public void addDoubleFire(int doubleFire) {
		this.doubleFire += doubleFire;
	}

	/**
	 * 获取英雄飞机双倍射速时间
	 * 
	 * @return int
	 */
	public int getDoubleFireSpeed() {
		return doubleFireSpeed;
	}

	/**
	 * 获取英雄飞机双倍射速的时间
	 * 
	 * @param doubleFireSpeed
	 *            想要设置的时间，整数类型
	 */
	public void addDoubleFireSpeed(int doubleFireSpeed) {
		this.doubleFireSpeed += doubleFireSpeed;
	}

	/**
	 * 重写越界检测，英雄飞机不会越界，所以永远返回false
	 */
	@Override
	public boolean outOfBound() {
		return false;
	}

	/**
	 * 英雄飞机移动方法
	 */
	@Override
	public void move() {
		if (life > 0) {
			image = Setting.heros_normal[planeImage++ % 2];
		} else {
			if (!death) {
				this.image = deathImages[deathImages.length - imageNum];
				deathIndex--;
				if (deathIndex == 0) {
					imageNum--;
					deathIndex = 3;
				}
				if (imageNum < 1) {
					death = true;
				}
			}
		}
	}

	/**
	 * 英雄飞机移动位置
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void moveTo(int x, int y) {
		if (life <= 0) {
			return;
		}
		this.x = x - this.image.getWidth(null) / 2;
		this.y = y - this.image.getHeight(null) / 2;
	}

	/**
	 * 发射子弹 默认一次发射一发子弹，若为双倍攻击状态则发射两发子弹
	 * 
	 * @return
	 */
	public Bullet[] shootBullet() {
		if (life <= 0) {
			return new Bullet[0];
		}
		/**
		 * 子弹相对于飞机的偏移量
		 */
		int offset = this.height / 2;
		Bullet[] bs;

		// 判断是否有双倍火力，如果有双倍火力，优先双倍子弹
		if (this.doubleFire > 0) {
			bs = new Bullet[2];
			// 发射两颗子弹
			Bullet b1 = this.doubleFireSpeed > 0
					? new Bullet(x + image.getWidth(null) / 2 - width * 4 / 3, this.y - offset)
					: new Bullet(x + image.getWidth(null) / 2 - width * 4 / 3, this.y + offset, true);
			Bullet b2 = this.doubleFireSpeed > 0
					? new Bullet(x + image.getWidth(null) / 2 + width * 4 / 3, this.y - offset)
					: new Bullet(x + image.getWidth(null) / 2 + width * 4 / 3, this.y + offset, true);
			bs[0] = b1;
			bs[1] = b2;

			this.doubleFire -= 2;
		} else {
			bs = new Bullet[1];
			// 发射一颗子弹
			Bullet b = this.doubleFireSpeed > 0 ? new Bullet(x + image.getWidth(null) / 2, this.y - offset)
					: new Bullet(x + image.getWidth(null) / 2, this.y - offset, true);
			bs[0] = b;
		}
		if (this.doubleFireSpeed > 0) {
			this.doubleFireSpeed -= 2;
		}

		return bs;
	}

	/**
	 * 增加英雄飞机的生命值
	 * 
	 * @param addNum
	 *            想要增加的英雄飞机生命值数量，整数类型
	 */
	public void addLife(int addNum) {
		this.life += addNum;
	}
}

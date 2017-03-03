package com.crazybun.bean;

import com.crazybun.utils.Setting;

/**
 * buff物品类
 * 
 * @author CrazyBun
 */
public class Buff extends FlyObjects implements Award {
	/**
	 * buff类型
	 */
	private double type;
	// 控制buff移动方向
	private boolean backX;
	private boolean backY;

	public Buff(int x, int y) {
		/**
		 * 奖励类型随机
		 */
		type = Math.random();
		image = type < 0.5 ? Setting.buff1 : Setting.buff2;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
		speed = Setting.SPEED_BUFF;
		// 初始方向随机
		this.backX = Math.random() < 0.5f ? true : false;
		this.backY = Math.random() < 0.5f ? true : false;
	}

	/**
	 * buff的移动方式
	 */
	@Override
	public void move() {
		x = backX ? x - speed : x + speed;
		y = backY ? y - speed * 2 : y + speed * 2;
	}

	/**
	 * 重写outOfBound方法，使buff撞墙后可以反弹
	 */
	@Override
	public boolean outOfBound() {
		backX = x < 0 ? false : (x > Setting.FRAME_WIDTH - width * 3 / 2 ? true : backX);
		backY = y < 0 ? false : (y > Setting.FRAME_HEIGHT - height * 3 ? true : backY);
		return false;
	}

	/**
	 * 获取buff类型
	 * 
	 * @return double
	 */
	public double getType() {
		return type;
	}
}

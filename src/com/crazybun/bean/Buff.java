package com.crazybun.bean;

import com.crazybun.utils.Setting;

/**
 * buff物品类
 * 
 * @author CrazyBun
 */
public class Buff extends FlyItems implements Award {
	/**
	 * buff类型
	 */
	private int type;
	// 控制buff移动方向
	private boolean backX;
	private boolean backY;

	public Buff(int x, int y) {
		/**
		 * 奖励类型随机
		 */
		type = Setting.RND.nextInt(Award.TOTAL_AWARD_TYPES);
		switch (type) {
		case 0:
			image = Setting.buff1;
			break;
		case 1:
			image = Setting.buff2;
			break;
		case 2:
			image = Setting.buff3;
			break;
		default:
			break;
		}
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
	 * @return int
	 */
	public int getType() {
		return type;
	}
}

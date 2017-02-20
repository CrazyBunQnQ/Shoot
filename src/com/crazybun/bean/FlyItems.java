package com.crazybun.bean;

import java.awt.Image;

import com.crazybun.utils.Setting;

/**
 * 所有飞行物品的父类
 * 
 * @author CrazyBun
 */
public abstract class FlyItems {
	// portected 受保护的变量
	/**
	 * 飞行物品的图片
	 */
	protected Image image;
	/**
	 * 飞行物品的横坐标
	 */
	protected int x;
	/**
	 * 飞行物品的纵坐标
	 */
	protected int y;
	/**
	 * 物品的额宽度，用来检测碰撞
	 */
	protected int width;
	/**
	 * 物品的额高度，用来检测碰撞
	 */
	protected int height;
	/**
	 * 物品的速度
	 */
	protected int speed;

	// abstract静态变量或方法，在当前类中不设置具体的值或方法体，由子类来实现
	/**
	 * 物品的运动
	 */
	public abstract void move();

	/**
	 * 碰撞检测 检测两个物品是否相撞，如子弹与飞机，飞机与飞机，飞机与增益 return boolean
	 * 
	 * @param item
	 *            另一个飞行物品
	 */
	public boolean collisionDetection(FlyItems item) {
		boolean RlL = this.getRight() > item.getLeft();
		boolean RsR = this.getRight() < item.getRight();
		boolean TlT = this.getTop() > item.getTop();
		boolean TsB = this.getTop() < item.getBottom();
		boolean LlL = this.getLeft() > item.getLeft();
		boolean LsR = this.getLeft() < item.getRight();
		boolean BlT = this.getBottom() > item.getTop();
		boolean BsB = this.getBottom() < item.getBottom();

		boolean hitWithLeft = RlL && RsR && ((TlT && TsB) || (BlT && BsB));
		boolean hitWithRight = LlL && LsR && ((TlT && TsB) || (BlT && BsB));
		return hitWithLeft || hitWithRight;
	};

	/**
	 * 越界检测 检测物品是否超出画布
	 * 
	 * @return boolean
	 */
	public boolean outOfBound() {
		return this.getTop() - this.height * 2 > Setting.FRAME_HEIGHT || this.getBottom() + this.height * 2 < 0
				|| this.getLeft() - this.width * 2 > Setting.FRAME_WIDTH || this.getRight() + this.width * 2 < 0;
	}

	/**
	 * 获取物品的左边界
	 * 
	 * @return int
	 */
	public int getLeft() {
		return this.x - this.width / 2;
	}

	public int getRight() {
		return this.x + this.width / 2;
	}

	public int getTop() {
		return this.y - this.height / 2;
	}

	public int getBottom() {
		return this.y + this.height / 2;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

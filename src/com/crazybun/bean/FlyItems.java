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
	 * 物品的碰撞面积
	 */
	protected int S;
	/**
	 * 物品的速度
	 */
	protected int speed;
	/**
	 * 物品的生命值
	 */
	protected int life;
	/**
	 * 是否死亡
	 */
	protected boolean death;
	/**
	 * 死亡计数器
	 */
	protected int deathIndex;

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
		if (this.death || item.death) {
			return false;
		}
		boolean RlL = this.getS() < item.getS() ? this.getRight() > item.getLeft() : item.getRight() > this.getLeft();
		boolean RsR = this.getS() < item.getS() ? this.getRight() < item.getRight() : item.getRight() < this.getRight();
		boolean TlT = this.getS() < item.getS() ? this.getTop() > item.getTop() : item.getTop() > this.getTop();
		boolean TsB = this.getS() < item.getS() ? this.getTop() < item.getBottom() : item.getTop() < this.getBottom();
		boolean LlL = this.getS() < item.getS() ? this.getLeft() > item.getLeft() : item.getLeft() > this.getLeft();
		boolean LsR = this.getS() < item.getS() ? this.getLeft() < item.getRight() : item.getLeft() < this.getRight();
		boolean BlT = this.getS() < item.getS() ? this.getBottom() > item.getTop() : item.getBottom() > this.getTop();
		boolean BsB = this.getS() < item.getS() ? this.getBottom() < item.getBottom()
				: item.getBottom() < this.getBottom();

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
	 * 获取左上角x坐标 此坐标为图片的左上角x坐标，并非是碰撞体积的
	 * 
	 * @return int
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * 获取左上角y坐标，此坐标为图片的左上角y坐标，并非是碰撞体积的
	 * 
	 * @return int
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * 获取物品的左边框位置 ，等同于获取物品碰撞体积的左上角坐标x的值
	 * 
	 * @return int
	 */
	public int getLeft() {
		if (image == null) {
			return 0;
		}
		return this.x + this.image.getWidth(null) / 2 - this.width / 2;
	}

	/**
	 * 获取物品的右边框位置，等同于获取物品碰撞体积的右下角坐标x的值
	 * 
	 * @return int
	 */
	public int getRight() {
		if (image == null) {
			return 0;
		}
		return this.x + this.image.getWidth(null) / 2 + this.width / 2;
	}

	/**
	 * 获取物品的上边框位置，等同于获取物品碰撞体积的左上角坐标y的值
	 * 
	 * @return int
	 */
	public int getTop() {
		if (image == null) {
			return 0;
		}
		return this.y + this.image.getHeight(null) / 2 - this.height / 2;
	}

	/**
	 * 获取物品的下边框位置，等同于获取物品碰撞体积的右下角坐标y的值
	 * 
	 * @return int
	 */
	public int getBottom() {
		if (image == null) {
			return 0;
		}
		return this.y + this.image.getHeight(null) / 2 + this.height / 2;
	}

	/**
	 * 获取资源图片
	 * 
	 * @return Image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * 获取物品移动速度
	 * 
	 * @return int
	 */
	public int getSpeed() {
		return speed;
	}

//	/**
//	 * 设置物品的生命值
//	 * 
//	 * @param life
//	 */
//	public void setLife(int life) {
//		this.life = life;
//	}

	/**
	 * 获取物品的生命值
	 */
	public int getLife() {
		return life;
	}

	/**
	 * 获取物品碰撞面积
	 * 
	 * @return int
	 */
	public int getS() {
		return width * height;
	}
	
	/**
	 * 减少物品的生命值
	 */
	public void subLife(int n) {
		this.life -= n;
	}
	
	/**
	 * 物品是否死亡
	 * @return
	 */
	public boolean isDeath(){
		return death;
	}
}

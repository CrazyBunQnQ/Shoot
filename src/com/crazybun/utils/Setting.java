package com.crazybun.utils;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * 游戏设置类
 * 
 * @author CrazyBun
 */
public class Setting {
	/**
	 * 主窗口的宽
	 */
	public static final int FRAME_WIDTH = 400;
	/**
	 * 主窗口的高
	 */
	public static final int FRAME_HEIGHT = 660;

	// 图片资源
	public static Image backGround;
	public static Image gameStart;
	public static Image gameOver;
	public static Image gamePause;
	public static Image heroPlane1;
	public static Image heroPlane2;
	public static Image enemyPlane1;
	public static Image enemyPlane2;
	public static Image heroBullet;
	public static Image enemyBullet;
	public static Image awardPlane;

	// 图片数组
	public static Image[] enemies;
	public static Image[] heros;

	/**
	 * 随机数
	 */
	public static final Random RND = new Random();
	/**
	 * 敌方飞机速度
	 */
	public static final int SPEED_ENEMYPLANE = 2;
	/**
	 * 奖励飞机速度
	 */
	public static final int SPEED_AWARDPLANE = 2;
	/**
	 * buff速度
	 */
	public static final int SPEED_BUFF = 1;
	/**
	 * 敌方子弹速度
	 */
	public static final int SPEED_ENEMY_BULLET = 3;
	/*
	 * 我方子弹速度
	 */
	public static final int SPEED_HERO_BULLET = 3;
	// 英雄飞机初始位置
	public static final int HERO_INIT_X = FRAME_WIDTH / 2;
	public static final int HERO_INIT_Y = FRAME_HEIGHT * 2 / 3;
	/*
	 * 初始的生命
	 */
	public static final int HERO_INIT_LIFE = 3;
	/**
	 * 双倍攻击
	 */
	public static final int HERO_INIT_DOUBLE = 20;

	/**
	 * 双倍射速
	 */
	public static final boolean HERO_INIT_FIRE_SPEED = false;

	static {
		backGround = new ImageIcon("images/backGround.gif").getImage();
		gameStart = new ImageIcon("images/gameStart.png").getImage();
		gameOver = new ImageIcon("images/gameOver.png").getImage();
		gamePause = new ImageIcon("images/pause.png").getImage();
		heroPlane1 = new ImageIcon("images/heroPlane1.png").getImage();
		heroPlane2 = new ImageIcon("images/heroPlane2.png").getImage();
		enemyPlane1 = new ImageIcon("images/enemyPlane1.png").getImage();
		enemyPlane2 = new ImageIcon("images/enemyPlane2.png").getImage();
		heroBullet = new ImageIcon("images/heroBullet.png").getImage();
		enemyBullet = new ImageIcon("images/emenyBullet.png").getImage();
		awardPlane = new ImageIcon("images/awardPlane.png").getImage();
//		enemies = new Image[]{enemyPlane1,enemyPlane2};
		heros = new Image[]{heroPlane1,heroPlane2};
	}
}

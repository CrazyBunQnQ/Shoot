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
	//=========================常量======================
	/**
	 * 主窗口的宽
	 */
	public static final int FRAME_WIDTH = 400;
	/**
	 * 主窗口的高
	 */
	public static final int FRAME_HEIGHT = 660;
	/**
	 * 游戏难度提升分段，每DIFFICULTY分提升一个难度
	 */
	public static final int INTERVAL = 100;
	/**
	 * 随机数
	 */
	public static final Random RND = new Random();
	/**
	 * Boss飞机速度
	 */
	public static final int SPEED_BOSSPLANE = 1;
	/**
	 * 敌方飞机速度
	 */
	public static final int SPEED_ENEMYPLANE = 2;
	/**
	 * 奖励飞机速度
	 */
	public static final int SPEED_AWARDPLANE = 3;
	/**
	 * buff速度
	 */
	public static final int SPEED_BUFF = 1;
	/**
	 * 敌方子弹速度
	 */
	public static final int SPEED_ENEMY_BULLET = 4;
	/*
	 * 我方子弹速度
	 */
	public static final int SPEED_HERO_BULLET_MOVE = 14;
	/**
	 * 我方子弹发射频率（越小越快）
	 */
	public static final int SPEED_HERO_BULLET_CREATE = 10;
	/**
	 * 敌方飞机子弹发射频率（越小越快）
	 */
	public static final int SPEED_ENEMY_BULLET_CREATE = 120;
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
	public static final int HERO_INIT_DOUBLE = 100;
	
	/**
	 * 双倍射速
	 */
	public static final int HERO_INIT_FIRE_SPEED = 100;

	//=====================静态变量===================
	// 图片资源
	public static Image backGround;
	public static Image gameStart;
	public static Image gameOver;
	public static Image gamePause;
	public static Image heroPlane1;
	public static Image smallPlane;
	public static Image midPlane;
	public static Image bossPlane;
	public static Image heroBullet;
	public static Image enemyBullet;
	public static Image awardPlane;
	public static Image buff1;
	public static Image buff2;
	public static Image buff3;
	// 图片数组
	public static Image[] smallDeath;
	public static Image[] midDeath;
	public static Image[] bossDeath;
	public static Image[] heros_normal;
	public static Image[] heros_death;
	
	/**
	 * 静态代码块，只执行一次,初始化图片资源
	 */
	static {
		backGround = new ImageIcon("images/backGround.gif").getImage();
		gameStart = new ImageIcon("images/gameStart.png").getImage();
		gameOver = new ImageIcon("images/gameOver.png").getImage();
		gamePause = new ImageIcon("images/pause.png").getImage();
		heroPlane1 = new ImageIcon("images/heroPlane1.png").getImage();
		smallPlane = new ImageIcon("images/smallPlane1.png").getImage();
		midPlane = new ImageIcon("images/midPlane1.png").getImage();
		bossPlane = new ImageIcon("images/bossPlane1.png").getImage();
		heroBullet = new ImageIcon("images/heroBullet.png").getImage();
		enemyBullet = new ImageIcon("images/enemyBullet.png").getImage();
		awardPlane = new ImageIcon("images/awardPlane.png").getImage();
		buff1 = new ImageIcon("images/buff1.png").getImage();
		buff2 = new ImageIcon("images/buff2.png").getImage();
//		buff3 = new ImageIcon("images/buff3.png").getImage();
		smallDeath = new Image[]{
				smallPlane, 
				new ImageIcon("images/smallPlane2.png").getImage(),
				new ImageIcon("images/smallPlane3.png").getImage(),
				new ImageIcon("images/smallPlane4.png").getImage(),
				new ImageIcon("images/smallPlane5.png").getImage()
				};
		midDeath = new Image[]{
				midPlane,
				new ImageIcon("images/midPlane2.png").getImage(),
				new ImageIcon("images/midPlane3.png").getImage(),
				new ImageIcon("images/midPlane4.png").getImage(),
				new ImageIcon("images/midPlane5.png").getImage(),
				new ImageIcon("images/midPlane6.png").getImage()
		};
		bossDeath = new Image[]{
				bossPlane,
				new ImageIcon("images/bossPlane2.png").getImage(),
				new ImageIcon("images/bossPlane3.png").getImage(),
				new ImageIcon("images/bossPlane4.png").getImage(),
				new ImageIcon("images/bossPlane5.png").getImage(),
				new ImageIcon("images/bossPlane6.png").getImage(),
				new ImageIcon("images/bossPlane7.png").getImage(),
				new ImageIcon("images/bossPlane8.png").getImage(),
				new ImageIcon("images/bossPlane9.png").getImage()
		};
		heros_normal = new Image[]{heroPlane1,new ImageIcon("images/heroPlane2.png").getImage()};
		heros_death = new Image[]{
				new ImageIcon("images/heroDeath1.png").getImage(),
				new ImageIcon("images/heroDeath2.png").getImage(),
				new ImageIcon("images/heroDeath3.png").getImage(),
				new ImageIcon("images/heroDeath4.png").getImage()
		};
		
	}
}

package com.crazybun.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.crazybun.bean.Buff;
import com.crazybun.bean.Bullet;
import com.crazybun.bean.EnemyPlane;
import com.crazybun.bean.FlyItems;
import com.crazybun.bean.HeroPlane;
import com.crazybun.logic.ActionLogic;
import com.crazybun.logic.CreateLogic;
import com.crazybun.logic.HitLogic;
import com.crazybun.utils.Setting;

/**
 * 游戏的主面板，用来显示游戏画面
 * 
 * @author CrazyBun
 */
public class MainPanel extends JPanel {
	private static final long serialVersionUID = -7474606327455623757L;
	// 游戏的四个状态
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAMEOVER = 3;

	/**
	 * 英雄飞机
	 */
	public static HeroPlane hero;
	public static int heroBulletCreateSpeed;
	/**
	 * 敌方飞机数组
	 */
	public static EnemyPlane[] enemyPlanes;
	/**
	 * 所有的子弹数组
	 */
	public static Bullet[] bullets;
	/**
	 * 所有buff的数组
	 */
	public static Buff[] buffs;
	/**
	 * 正在死亡的飞机，等待删除的飞机
	 */
	public static ArrayList<EnemyPlane> deathPlanes;
	/**
	 * 计时器
	 */
	private Timer timer = new Timer();
	/**
	 * 敌方飞机的下标
	 */
	public static int planeIndex = 0;
	/**
	 * 子弹下标
	 */
	public static int bulletIndex = 0;
	/**
	 * 获得的分数
	 */
	public static int score;

	/**
	 * 游戏难度等级
	 */
	public static int difficulty;
	/**
	 * 游戏的状态
	 */
	private int state;

	/**
	 * 构造MainPanel类
	 */
	public MainPanel() {
		restart();
		state = START;
	}

	/**
	 * JPanel中的绘制方法 paint方法不能手动调用，系统会自动调用该方法
	 * 
	 * @param g
	 *            画笔
	 */
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintPlanes(g);
		paintHero(g);
		paintBullets(g);
		paintBuffs(g);
		paintScore(g);
		paintState(g);
	}

	/**
	 * 绘制背景
	 * 
	 * @param g
	 */
	private void paintBackground(Graphics g) {
		g.drawImage(Setting.backGround, 0, 0, this);
	}

	/**
	 * 绘制英雄
	 * 
	 * @param g
	 */
	private void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), this);
	}

	/**
	 * 绘制飞机
	 * 
	 * @param g
	 */
	private void paintPlanes(Graphics g) {
		for (int i = 0; i < enemyPlanes.length; i++) {
			FlyItems fi = enemyPlanes[i];
			g.drawImage(fi.getImage(), fi.getX(), fi.getY(), this);
		}
	}

	/**
	 * 绘制子弹
	 * 
	 * @param g
	 */
	private void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i] != null) {
				g.drawImage(bullets[i].getImage(), bullets[i].getX(), bullets[i].getY(), this);
			}
		}
	}

	/**
	 * 绘制buff
	 * 
	 * @param g
	 */
	private void paintBuffs(Graphics g) {
		for (int i = 0; i < buffs.length; i++) {
			Buff buff = buffs[i];
			g.drawImage(buff.getImage(), buff.getX(), buff.getY(), this);
		}
	}

	/**
	 * 绘制分数生命
	 * 
	 * @param g
	 */
	private void paintScore(Graphics g) {
		Font font = new Font("Consolas", Font.BOLD, 16);
		g.setFont(font);
		g.setColor(Color.GRAY);
		g.drawString("SCORE: " + score, 2, 20);
		g.drawString("LIFE: " + (hero.getLife() < 0 ? 0 : hero.getLife()), 2, 40);
		g.drawString("DIFFICULTY: " + difficulty, 270, 20);
		if (hero.getDoubleFire() > 0) {
			if (hero.getDoubleFireSpeed() > 0) {
				g.drawString("FIRE AND SPEED UP!", 2, 60);
			} else {
				g.drawString("FIRE UP", 2, 60);
			}
		} else if (hero.getDoubleFireSpeed() > 0) {
			g.drawString("SPEED UP", 2, 60);
		}
	}

	/**
	 * 绘制当前状态
	 * 
	 * @param g
	 */
	private void paintState(Graphics g) {
		switch (state) {
		case PAUSE:
			g.drawImage(Setting.gamePause, 0, 0, this);
			break;

		case GAMEOVER:
			g.drawImage(Setting.gameOver, 0, 0, this);
			break;

		case START:
			g.drawImage(Setting.gameStart, 0, 0, this);
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化和重置游戏画面
	 */
	protected void restart() {
		hero = new HeroPlane();
		enemyPlanes = new EnemyPlane[] {};
		bullets = new Bullet[] {};
		buffs = new Buff[] {};
		deathPlanes = new ArrayList<>();
		score = 0;
		difficulty = 1;
		heroBulletCreateSpeed = Setting.SPEED_HERO_BULLET_CREATE;
	}

	/**
	 * 运行游戏
	 */
	public void run() {
		MouseAdapter ma = new MouseAdapter() {
			/**
			 * 鼠标移动事件
			 */
			@Override
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {
					int mouseX = e.getX();
					int mouseY = e.getY();
					hero.moveTo(mouseX, mouseY);
				}
			}

			/**
			 * 鼠标点击事件
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (state == START) {
					state = RUNNING;
					return;
				}

				if (state == GAMEOVER) {
					state = START;
					restart();
					return;
				}
			}

			/**
			 * 鼠标离开事件
			 */
			@Override
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}

			/**
			 * 鼠标进入事件
			 */
			@Override
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
				}
			}
		};

		this.addMouseMotionListener(ma);
		this.addMouseListener(ma);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) {
					CreateLogic.createEnemyPlanes();
					ActionLogic.move();
					CreateLogic.createBullets();
					ActionLogic.hitByBullet();
					ActionLogic.deleteItems();
					ActionLogic.deleteEnemyPlanesInPanel();
					HitLogic.heroHitEnemyPlane();
					HitLogic.getBuff();
					GameOverAction();
					// System.out.println("Enemy Planes: " + enemyPlanes.length
					// + " Bullets: " + bullets.length
					// + " Buffs: " + buffs.length);
				}
				repaint();
			}
		}, 0, 20);
	}

	/**
	 * 判断游戏是否结束的事件
	 */
	protected void GameOverAction() {
		if (hero.getLife() <= 0) {
			state = GAMEOVER;
		}
	}
}

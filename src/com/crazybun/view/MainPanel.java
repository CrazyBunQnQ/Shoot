package com.crazybun.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.crazybun.bean.Buff;
import com.crazybun.bean.Bullet;
import com.crazybun.bean.EnemyPlane;
import com.crazybun.bean.FlyItems;
import com.crazybun.bean.HeroPlane;
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
	private HeroPlane hero;
	private int heroBulletCreateSpeed;
	/**
	 * 敌方飞机数组
	 */
	private EnemyPlane[] enemyPlanes;
	/**
	 * 所有的子弹数组
	 */
	private Bullet[] bullets;
	/**
	 * 所有buff的数组
	 */
	private Buff[] buffs;
	/**
	 * 计时器
	 */
	private Timer timer = new Timer();
	/**
	 * 敌方飞机的下标
	 */
	private int planeIndex = 0;
	/**
	 * 子弹下标
	 */
	private int bulletIndex = 0;
	/**
	 * 获得的分数
	 */
	private int score;
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
	 * 初始化和重置游戏画面
	 */
	protected void restart() {
		hero = new HeroPlane();
		enemyPlanes = new EnemyPlane[] {};
		bullets = new Bullet[] {};
		buffs = new Buff[] {};
		score = 0;
		heroBulletCreateSpeed = Setting.SPEED_HERO_BULLET_CREATE;
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
		g.drawImage(hero.getImage(), hero.getLeft(), hero.getTop(), this);
	}

	/**
	 * 绘制飞机
	 * 
	 * @param g
	 */
	private void paintPlanes(Graphics g) {
		for (int i = 0; i < enemyPlanes.length; i++) {
			FlyItems fi = enemyPlanes[i];
			g.drawImage(fi.getImage(), fi.getLeft(), fi.getTop(), this);
		}
	}

	/**
	 * 绘制子弹
	 * 
	 * @param g
	 */
	private void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet bullet = bullets[i];
			g.drawImage(bullet.getImage(), bullet.getLeft(), bullet.getTop(), this);
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
			g.drawImage(buff.getImage(), buff.getLeft(), buff.getTop(), this);
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
		g.drawString("LIFE: " + hero.getLife(), 2, 40);
		// g.drawString(" FIRE: ", 2, 60);
		// g.drawString("SPEED: ", 2, 80);
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
					enterAction();
					moveAction();
					shootAction();
					hitAction();
					deleteAction();
					// heroHitEnemyBulletAction();
					heroHitEnemyPlaneAction();
					getBuffAction();
					GameOverAction();
					// System.out.println("Enemy Planes: " +
					// enemyPlanes.length);
					// System.out.println("Bullets: " + bullets.length);
				}
				repaint();
			}
		}, 0, 20);
	}

	/**
	 * 生成敌方飞机
	 */
	protected void enterAction() {
		if (planeIndex++ % 30 != 0) {
			return;
		}
		EnemyPlane fi;
		int index = Setting.RND.nextInt(20);
		if (index < 2) {
			fi = new EnemyPlane(true);
		} else {
			fi = new EnemyPlane(false);
		}

		enemyPlanes = Arrays.copyOf(enemyPlanes, enemyPlanes.length + 1);
		enemyPlanes[enemyPlanes.length - 1] = fi;
	}

	/**
	 * 物品移动 包括英雄飞机，敌方飞机和所有子弹的移动
	 */
	protected void moveAction() {
		hero.move();
		for (int i = 0; i < enemyPlanes.length; i++) {
			enemyPlanes[i].move();
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].move();
		}
		for (int i = 0; i < buffs.length; i++) {
			buffs[i].move();
		}
	}

	/**
	 * 射击 生成英雄飞机的子弹和敌方飞机的子弹
	 */
	protected void shootAction() {
		heroBulletCreateSpeed = hero.getDoubleFireSpeed() > 0 ? Setting.SPEED_HERO_BULLET_CREATE / 2
				: Setting.SPEED_HERO_BULLET_CREATE;
		if (bulletIndex++ % heroBulletCreateSpeed != 0) {
			return;
		}
		Bullet[] bs = hero.shootBullet();
		bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
		System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);

		int index = 0;
		if ((bulletIndex - 1) % 120 == 0) {
			bs = new Bullet[enemyPlanes.length];
			for (int i = 0; i < enemyPlanes.length; i++) {
				if (enemyPlanes[i].isPowerfulPlane()) {
					bs[index] = enemyPlanes[i].shootBullet();
					index++;
				}
			}
			// System.out.println(bullets.length + bullets[bullets.length -
			// 1].toString());
			if (index > 0) {
				bullets = Arrays.copyOf(bullets, bullets.length + index);
				System.arraycopy(bs, 0, bullets, bullets.length - index, index);
			}
			// System.out.println(bullets.length + bullets[bullets.length -
			// 1].toString());
		}
	}

	/**
	 * 子弹命中事件
	 */
	protected void hitAction() {
		Bullet[] bulletLives = new Bullet[bullets.length];
		int index = 0;
		for (int i = 0; i < bullets.length; i++) {
			Bullet bullet = bullets[i];
			if (!isHit(bullet, bullet.isHero())) {
				bulletLives[index++] = bullet;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
	}

	/**
	 * 子弹是否命中 包括敌方子弹是否命中英雄飞机以及英雄飞机的子弹是否命中敌方飞机
	 * 
	 * @param bullet
	 *            子弹
	 * @param isHero
	 *            是否英雄飞机的子弹
	 * @return 是否命中
	 */
	private boolean isHit(Bullet bullet, boolean isHero) {
		EnemyPlane ep = null;
		boolean isHit = false;
		if (isHero) {
			for (int i = 0; i < enemyPlanes.length; i++) {
				ep = enemyPlanes[i];
				isHit = bullet.collisionDetection(ep);
				if (isHit) {
					if (ep.isPowerfulPlane()) {
						Buff buff = new Buff(ep.getLeft(), ep.getTop());
						buffs = Arrays.copyOf(buffs, buffs.length + 1);
						buffs[buffs.length - 1] = buff;
					}
					deletePlanes(i);
					break;
				}
			}
		} else {
			isHit = bullet.collisionDetection(hero);
			if (isHit) {
				hero.subLife();
			}
		}
		return isHit;
	}

	/**
	 * 删除越界的物品 包括越界的子弹和飞机
	 */
	protected void deleteAction() {
		// 删除越界的飞机
		EnemyPlane[] fiLives = new EnemyPlane[enemyPlanes.length];
		int index = 0;
		for (int i = 0; i < enemyPlanes.length; i++) {
			EnemyPlane fi = enemyPlanes[i];
			if (!fi.outOfBound()) {
				fiLives[index++] = fi;
			}
		}
		enemyPlanes = Arrays.copyOf(fiLives, index);

		// 删除越界的子弹
		Bullet[] buttleLives = new Bullet[bullets.length];
		index = 0;
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBound()) {
				buttleLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(buttleLives, index);
		for (int i = 0; i < buffs.length; i++) {
			buffs[i].outOfBound();
		}
	}

	/**
	 * 英雄飞机撞击敌方飞机事件
	 */
	protected void heroHitEnemyPlaneAction() {
		for (int i = 0; i < enemyPlanes.length; i++) {
			EnemyPlane ep = enemyPlanes[i];
			if (ep.collisionDetection(hero)) {
				hero.subLife();
				// 将被撞的飞机抹除
				deletePlanes(i);
			}
		}
	}

	protected void getBuffAction() {
		for (int i = 0; i < buffs.length; i++) {
			Buff buff = buffs[i];
			if (buff.collisionDetection(hero)) {
				switch (buff.getType()) {
				case 0:
					hero.setDoubleFire(40);
					break;
				case 1:
					hero.setDoubleFireSpeed(40);
					break;
				case 2:
					hero.addLife(1);
					break;
				default:
					break;
				}
				buffs[i] = buffs[buffs.length - 1];
				buffs = Arrays.copyOf(buffs, buffs.length - 1);
			}
		}
	}

	/**
	 * 判断游戏是否结束的事件
	 */
	protected void GameOverAction() {
		if (hero.getLife() < 0) {
			state = GAMEOVER;
		}
	}

	/**
	 * 删除数组中的飞机
	 * 
	 * @param i
	 *            数组下标
	 */
	private void deletePlanes(int i) {
		score += enemyPlanes[i].isPowerfulPlane() ? 5 : 2;
		enemyPlanes[i] = enemyPlanes[enemyPlanes.length - 1];
		enemyPlanes = Arrays.copyOf(enemyPlanes, enemyPlanes.length - 1);
	}
}

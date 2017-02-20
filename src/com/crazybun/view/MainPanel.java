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
	// 游戏的四个状态
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAMEOVER = 3;

	private static final long serialVersionUID = -7474606327455623757L;
	private HeroPlane hero;
	private EnemyPlane[] enemyPlanes;
	private Bullet[] bullets;
	private Timer timer = new Timer();
	private int planeIndex = 0;
	private int bulletIndex = 0;
	private int score;
	private int state;

	public MainPanel() {
		restart();
		state = START;
	}

	protected void restart() {
		hero = new HeroPlane();
		enemyPlanes = new EnemyPlane[] {};
		bullets = new Bullet[] {};
		score = 0;
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
		paintScore(g);
		paintState(g);
	}

	private void paintBackground(Graphics g) {
		g.drawImage(Setting.backGround, 0, 0, this);
	}

	private void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getLeft(), hero.getTop(), this);
	}

	private void paintPlanes(Graphics g) {
		for (int i = 0; i < enemyPlanes.length; i++) {
			FlyItems fi = enemyPlanes[i];
			g.drawImage(fi.getImage(), fi.getLeft(), fi.getTop(), this);
		}
	}

	private void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet bullet = bullets[i];
			g.drawImage(bullet.getImage(), bullet.getLeft(), bullet.getTop(), this);
		}
	}

	private void paintScore(Graphics g) {
		Font font = new Font("Consolas", Font.BOLD, 24);
		g.setFont(font);
		g.setColor(Color.GRAY);
		g.drawString("SCORE: ", 2, 25);
		g.drawString(" LIFE: ", 2, 55);
		// g.drawString(" FIRE: ", 2, 85);
		// g.drawString("SPEED: ", 2, 115);
	}

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

			@Override
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}

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
					GameOverAction();
					// System.out.println("Enemy Planes: " +
					// enemyPlanes.length);
					// System.out.println("Bullets: " + bullets.length);
				}
				repaint();
			}
		}, 0, 20);
	}

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

	protected void moveAction() {
		hero.move();
		for (int i = 0; i < enemyPlanes.length; i++) {
			enemyPlanes[i].move();
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].move();
		}
	}

	protected void shootAction() {
		if (bulletIndex++ % 30 != 0) {
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

	private boolean isHit(Bullet bullet, boolean isHero) {
		EnemyPlane ep = null;
		boolean isHit = false;
		if (isHero) {
			for (int i = 0; i < enemyPlanes.length; i++) {
				ep = enemyPlanes[i];
				isHit = ep.collisionDetection(bullet);
				if (isHit) {
					// TODO 删除之前判断飞机是否是奖励飞机
					deletePlanes(i);
					break;
				}
			}
		} else {
			isHit = hero.collisionDetection(bullet);
			if (isHit) {
				hero.subLife();
			}
		}
		return isHit;
	}

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
	}

	// protected void heroHitEnemyBulletAction() {
	// for (int i = 0; i < bullets.length; i++) {
	// FlyItems fi = bullets[i];
	// boolean isHit = FlyItems.collisionDetection(hero, fi);
	// if (isHit) {
	// hero.subLife();
	// // 将击中英雄飞机的子弹抹除
	// bullets[i] = bullets[bullets.length - 1];
	// bullets = Arrays.copyOf(bullets, bullets.length - 1);
	// }
	// }
	// }

	protected void heroHitEnemyPlaneAction() {
		for (int i = 0; i < enemyPlanes.length; i++) {
			EnemyPlane ep = enemyPlanes[i];
			boolean isHit = ep.collisionDetection(hero);
			if (isHit) {
				hero.subLife();
				// 将被撞的飞机抹除
				deletePlanes(i);
			}
		}
	}

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
		enemyPlanes[i] = enemyPlanes[enemyPlanes.length - 1];
		enemyPlanes = Arrays.copyOf(enemyPlanes, enemyPlanes.length - 1);
	}
}

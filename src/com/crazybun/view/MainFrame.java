package com.crazybun.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.crazybun.utils.ScreenUtil;
import com.crazybun.utils.Setting;

/**
 * 游戏主窗口
 * 
 * @author CrazyBun
 */
public class MainFrame {
	public static void main(String[] args) {
		init();
	}

	private static void init() {
		// 创建窗口对象
		JFrame frame = new JFrame();
		frame.setSize(Setting.FRAME_WIDTH, Setting.FRAME_HEIGHT);
		frame.setTitle("打飞机");
		frame.setIconImage(new ImageIcon("images/ico.png").getImage());
		// 居中显示窗口
		frame.setLocation((ScreenUtil.getScreenX() - Setting.FRAME_WIDTH) / 2, (ScreenUtil.getScreenY() - Setting.FRAME_HEIGHT) / 2);
		// 设置关闭窗口的同时关闭后台进程
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * 画板对象，用来显示游戏画面
		 */
		MainPanel mp = new MainPanel();

		//在主窗口种添加画板对象
		frame.add(mp);
		frame.setVisible(true);
		//运行画板
		mp.run();
		// System.out.println(10/5%2);
	}
}

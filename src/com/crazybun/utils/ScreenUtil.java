package com.crazybun.utils;

import java.awt.Toolkit;

/**
 * 屏幕工具
 * @author CrazyBun
 */
public class ScreenUtil {
	/**
	 * 获取屏幕分辨率宽度
	 * @return int
	 */
	public static int getScreenX() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	/**
	 * 获取屏幕分辨率高度
	 * @return int
	 */
	public static int getScreenY() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}
	
}

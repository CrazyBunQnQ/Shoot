package com.crazybun.bean;

/**
 * 奖励接口
 * 
 * @author CrazyBun
 */
public interface Award {
	// 奖励类型
	public static final int TYPE_DOUBLE_FIRE = 1;
	public static final int TYPE_DOUBLE_SPEED = 2;
	public static final int TYPE_LIFE = 3;
	
	/**
	 * 奖励总数
	 */
	public static final int TOTAL_AWARD_TYPES = 3;
}

package com.junjie.raffle;

/**
 * 抽奖配置
 * @author abel.lee
 *2014年12月30日 下午2:33:32
 */
public class RaffleConfig {
	/**
	 *奖品计算概率方式
	 * @author abel.lee
	 *2014年12月30日 下午2:32:36
	 */
	public  static  enum Algo{
		/**
		 * 概率计算时只考虑价格.
		 */
		PRICE,
		/**
		 * 只考虑数量
		 */
		QUANTITY,
		/**
		 * 
		 */
		/**
		 * 价格和数量一起考虑
		 */
		P_AND_Q,
		/**
		 * 中奖概率传入
		 */
		CUSTOM
	}
	protected  Algo algo = Algo.P_AND_Q;
	/**
	 * 中奖概率,用来控制总人数值
	 */
	private double probability = 1.0d;
	public Algo getAlgo() {
		return algo;
	}
	public void setAlgo(Algo algo) {
		this.algo = algo;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	
	
	
	

}

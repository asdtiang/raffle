package com.junjie.raffle;
/**
 * 奖品
 * @author abel.lee
 *2014年12月30日 下午2:37:07
 */
public class RafflePrize {
	private String name;
	private long price;
	private long quantity;
	/**
	 * 当算法为custom时需要传入此值
	 */
	private double probability;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	
	
	
	
	

}

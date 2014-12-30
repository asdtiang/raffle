package com.junjie.raffle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Raffle {
	private static final Logger log = LoggerFactory.getLogger(Raffle.class);
	private RaffleConfig raffleConfig;
	private Map<String,RafflePrize> prizeMap = new ConcurrentHashMap<String, RafflePrize>();
	private List<PrizeRegion> regionList = new ArrayList<PrizeRegion>();
	
	public synchronized  void computeProbability(){
		regionList.clear();
		compute();
		if(log.isInfoEnabled()){
			StringBuilder logStr = new StringBuilder();
			for(PrizeRegion prizeRegion:regionList){
				logStr.append(prizeRegion.rafflePrize.getName()+"floor ceil floor span\n");
				logStr.append(prizeRegion.floor+"----"+prizeRegion.ceil+":"+prizeRegion.floor+"  "+prizeRegion.probability+"\n");
			}
			log.info(logStr.toString());
		}
	}
	public synchronized RafflePrize play(){
		double random = Math.random();
		RafflePrize result = null;
		for(PrizeRegion prizeRegion:regionList){
			  if(random>=prizeRegion.floor&&random<prizeRegion.ceil){
				  Long prizeNo = prizeRegion.rafflePrize.getQuantity();
				  if(prizeNo>0){
					  result = prizeRegion.rafflePrize;
					  prizeNo--;
					  prizeRegion.rafflePrize.setQuantity(prizeNo);
				  }
				  if(prizeNo==0){
					  removePrize(prizeRegion.rafflePrize.getName());
				  }
			  }
		}
		return result;
	}
	private void compute() {
		double sum = 0l;
		for(RafflePrize rafflePrize:prizeMap.values()){
			switch(raffleConfig.getAlgo()){
			case PRICE :sum = sum+rafflePrize.getPrice();break;
			case QUANTITY :sum = sum+rafflePrize.getQuantity();break;
			case P_AND_Q :sum = sum +  rafflePrize.getQuantity()*rafflePrize.getPrice() ;break;
			case CUSTOM:sum = sum + rafflePrize.getProbability();break;
			default :sum = sum + rafflePrize.getProbability();break;
			}
		}
		sum = sum/raffleConfig.getProbability();
		double currentLocation = 0d;
		double tempSpan = 0d;
		for(RafflePrize rafflePrize:prizeMap.values()){
			PrizeRegion  prizeRegion = new PrizeRegion();
			prizeRegion.floor = currentLocation;
			prizeRegion.rafflePrize = rafflePrize;
			switch(raffleConfig.getAlgo()){
			case PRICE :tempSpan = rafflePrize.getPrice()/sum;;break;
			case QUANTITY :tempSpan = rafflePrize.getQuantity()/sum;;break;
			case P_AND_Q :tempSpan = (rafflePrize.getQuantity()*rafflePrize.getPrice())/sum; ;break;
			case CUSTOM:tempSpan = rafflePrize.getProbability()/sum;;break;
			default :tempSpan = rafflePrize.getProbability()/sum;;break;
			}
			prizeRegion.probability = tempSpan;
			currentLocation = currentLocation + tempSpan;
			prizeRegion.ceil = currentLocation;
			regionList.add(prizeRegion);
		}		
	}	
	public void updatePrize(String key,RafflePrize rafflePrize){
		if(prizeMap.put(key, rafflePrize)!=null){
			computeProbability();
		}
	}
	public synchronized void removePrize(String key){
		if(prizeMap.remove(key)!=null){
			computeProbability();
		}
	}
	public RaffleConfig getRaffleConfig() {
		return raffleConfig;
	}
	public void setRaffleConfig(RaffleConfig raffleConfig) {
		this.raffleConfig = raffleConfig;
	}
	public Map<String, RafflePrize> getPrizeMap() {
		return prizeMap;
	}
	public void setPrizeMap(Map<String, RafflePrize> prizeMap) {
		this.prizeMap = prizeMap;
	}
	public List<PrizeRegion> getRegionList() {
		return regionList;
	}

	class PrizeRegion{
		/** 
	     * 区间下限 
	     */  
	    private double floor;  
	      
	    /** 
	     * 区间上限 
	     */  
	    private double ceil;  
	      
	    /**
	     * 计算出的中奖概率
	     */
	    private double probability;
	    private RafflePrize rafflePrize;
	}
	
}

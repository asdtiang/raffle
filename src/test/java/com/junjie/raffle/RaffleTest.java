package com.junjie.raffle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.junjie.raffle.RaffleConfig.Algo;

public class RaffleTest{
	public static void main(String args[]){
		RaffleConfig raffleConfig = new RaffleConfig();
		raffleConfig.setAlgo(Algo.QUANTITY);
		raffleConfig.setProbability(1.0d);
		Map<String,RafflePrize> prizeMap = new ConcurrentHashMap<String, RafflePrize>();
		for(int i=1;i<5;i++){
			RafflePrize prize  = new RafflePrize();
			prize.setName("奖品"+i);
			prize.setPrice(100*i);
			prize.setQuantity(i*10);
			prizeMap.put(i+"", prize);
		}
		Raffle raffle = new Raffle();
		raffle.setPrizeMap(prizeMap);
		raffle.setRaffleConfig(raffleConfig);
		raffle.computeProbability();
		for(int i=0;i<10;i++){
			RafflePrize rafflePrize = raffle.play();
			if(rafflePrize!=null){
				System.out.println(rafflePrize.getName());
			}else{
				System.out.println("bad luck!!!!!!");
			}
			
		}
		
		
		}
	
}
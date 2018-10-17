package cn.zyblogs.example.disruptor.generate1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Trade {
	
	private String id;
	private String name;
	private double price;
	private AtomicInteger count = new AtomicInteger(0);
	

	  
}  
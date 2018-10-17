package cn.zyblogs.example.disruptor.multi;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {  
	
	private String id;//ID  
	private String name;
	private double price;//金额  
	
}
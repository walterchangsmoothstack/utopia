/**
 * 
 */
package com.ss.utopia.entity;

/**
 * @author Walter Chang
 *
 */
public class AirplaneType {
	private Integer airplaneTypeId;
	private Integer maxCapacity;
	
	public AirplaneType(Integer airplaneTypeId, Integer maxCapacity) {
		this.airplaneTypeId = airplaneTypeId;
		this.maxCapacity = maxCapacity;
	}
	
	public AirplaneType() {
		
	}
	
	public Integer getAirplaneTypeId() {
		return airplaneTypeId;
	}
	public void setAirplaneTypeId(Integer airplaneId) {
		this.airplaneTypeId = airplaneId;
	}
	public Integer getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
}

package wms.domain.model.order;

import wms.domain.model.cargo.CargoType;

public class StockOrderDetail {
	private int ammount;
	private CargoType type;
	
	public int getAmmount() {
		return ammount;
	}
	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}
	public CargoType getType() {
		return type;
	}
	public void setType(CargoType type) {
		this.type = type;
	}
	
	
}

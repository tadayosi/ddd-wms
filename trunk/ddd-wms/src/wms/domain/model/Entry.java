package wms.domain.model;

import wms.domain.model.cargo.CargoType;

public class Entry {
	private int ammount;
	private CargoType cargoType;

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	public CargoType getCargoType() {
		return cargoType;
	}

	public void setCargoType(CargoType cargoType) {
		this.cargoType = cargoType;
	}
}

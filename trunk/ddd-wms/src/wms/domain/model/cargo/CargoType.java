package wms.domain.model.cargo;

import wms.domain.model.shared.ValueObject;

public class CargoType implements ValueObject<CargoType> {

	private CargoSize size;
	
	public boolean sameValueAs(CargoType other) {
		// TODO Auto-generated method stub
		return false;
	}

	public CargoType() {}
	public CargoType(CargoSize size) {
		this.size = size;
	}
	
	public CargoSize getSize() {
		return size;
	}
	
	public static enum CargoSize {
		S("Small"), M("Medium"), L("Large");
		
		private String expression;
		
		CargoSize(String expression) {
			this.expression = expression;
		}
		
		public static CargoSize findValueFor(String expression) {
			for (CargoSize size : CargoSize.values()) {
				if (size.expression.equals(expression)) {
					return size;
				}
			}
			return null;
		}
	}
}

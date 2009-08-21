package wms.domain.model.basics;

/**
 * 物品や棚のサイズ
 * @author kentaro714
 */
public enum Size {
	S("Small"), M("Medium"), L("Large");
	
	private String expression;
	
	Size(String expression) {
		this.expression = expression;
	}
	
	public static Size findValueFor(String expression) {
		for (Size size : Size.values()) {
			if (size.expression.equals(expression)) {
				return size;
			}
		}
		return null;
	}
}
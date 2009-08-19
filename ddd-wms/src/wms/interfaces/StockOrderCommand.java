package wms.interfaces;

/**
 * UIから送信されたデータが格納される想定
 * @author kentaro
 *
 */
public class StockOrderCommand {
	public int ammount;
	public String size;
	
	public StockOrderCommand(String size, int ammount) {
		this.size = size;
		this.ammount = ammount;
	}
}

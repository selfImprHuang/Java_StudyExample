

package java8Lamda.entity;

/**
 * @date 2018-11-23 18:17
 */
public class CommonChargingView {

	private String startdate;
	private String enddate;
	private CharggingOrder order;

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public CharggingOrder getOrder() {
		return order;
	}

	public void setOrder(CharggingOrder order) {
		this.order = order;
	}
}

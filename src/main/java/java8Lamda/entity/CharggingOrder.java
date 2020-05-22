

package java8Lamda.entity;

/**
 * @author huangzj1
 * @date 2018-11-23 18:20
 */
public class CharggingOrder {

	private String id;
	private String orderInfo;
	private String chargeMethod;
	private String chargeExplanation;
	private String regionAndIsp;
	private String acceType;
	private String chargeValue;
	private String chargeCategory;
	private String siExcelHeader;
	private String chargeArea;
	private String channel;
	private String payAreaType;
	private String timePeriods;
	private ChargingType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getChargeMethod() {
		return chargeMethod;
	}

	public void setChargeMethod(String chargeMethod) {
		this.chargeMethod = chargeMethod;
	}

	public String getChargeExplanation() {
		return chargeExplanation;
	}

	public void setChargeExplanation(String chargeExplanation) {
		this.chargeExplanation = chargeExplanation;
	}

	public String getRegionAndIsp() {
		return regionAndIsp;
	}

	public void setRegionAndIsp(String regionAndIsp) {
		this.regionAndIsp = regionAndIsp;
	}

	public String getAcceType() {
		return acceType;
	}

	public void setAcceType(String acceType) {
		this.acceType = acceType;
	}

	public String getChargeValue() {
		return chargeValue;
	}

	public void setChargeValue(String chargeValue) {
		this.chargeValue = chargeValue;
	}

	public String getChargeCategory() {
		return chargeCategory;
	}

	public void setChargeCategory(String chargeCategory) {
		this.chargeCategory = chargeCategory;
	}

	public String getSiExcelHeader() {
		return siExcelHeader;
	}

	public void setSiExcelHeader(String siExcelHeader) {
		this.siExcelHeader = siExcelHeader;
	}

	public String getChargeArea() {
		return chargeArea;
	}

	public void setChargeArea(String chargeArea) {
		this.chargeArea = chargeArea;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPayAreaType() {
		return payAreaType;
	}

	public void setPayAreaType(String payAreaType) {
		this.payAreaType = payAreaType;
	}

	public String getTimePeriods() {
		return timePeriods;
	}

	public void setTimePeriods(String timePeriods) {
		this.timePeriods = timePeriods;
	}

	public ChargingType getType() {
		return type;
	}

	public void setType(ChargingType type) {
		this.type = type;
	}
}

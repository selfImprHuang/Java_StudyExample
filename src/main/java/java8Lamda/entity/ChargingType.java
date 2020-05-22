

package java8Lamda.entity;

import java.util.List;

/**
 * @date 2018-11-23 18:25
 */
public class ChargingType {

    private String name;

    private List<TimeText> detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeText> getDetail() {
        return detail;
    }

    public void setDetail(List<TimeText> detail) {
        this.detail = detail;
    }
}

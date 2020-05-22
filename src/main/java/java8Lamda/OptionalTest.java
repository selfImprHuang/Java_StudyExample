package java8Lamda;

import java8Lamda.entity.*;

import java.util.Optional;

public class OptionalTest {


    public static void main(String[] args) {
        System.out.println("测试Optional获取空属性");

        CommonResp<CommonChargingView> commonResp = new CommonResp<>();
        Provider<CommonChargingView> provider = new Provider<>();
        commonResp.setProvider(provider);

        CommonChargingView commonChargingView = Optional.ofNullable(commonResp).map(CommonResp::getProvider).map(Provider::getDate)
                .orElse(null);
        System.out.println(commonChargingView);

        System.out.println("测试Optional从null中提取属性");
        CharggingOrder charggingOrder = Optional.ofNullable(commonResp).map(CommonResp::getProvider).map(Provider::getDate).map
                (CommonChargingView::getOrder)
                .orElse(null);

        System.out.println(charggingOrder);

        System.out.println("测试Optional从值为null的集合中直接根据下标获取对应属性");
        TimeText timeText = Optional.ofNullable(commonResp).map(CommonResp::getProvider).map(Provider::getDate).map(CommonChargingView::getOrder).map
                (CharggingOrder::getType).map(type->type.getDetail().get(0)).orElse(null);
        System.out.println(timeText);
    }
}

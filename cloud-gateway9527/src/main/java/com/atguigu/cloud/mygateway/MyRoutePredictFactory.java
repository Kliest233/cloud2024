package com.atguigu.cloud.mygateway;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义配置会员等级userType
 */

@Component
public class MyRoutePredictFactory extends AbstractRoutePredicateFactory<MyRoutePredictFactory.Config> {


    public MyRoutePredictFactory(){
        super(MyRoutePredictFactory.Config.class);
    }

    //Config类就是路由断言规则
    @Validated
    public static class Config{
        @Setter@Getter@NotEmpty
        private String userType;
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredictFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
                if (userType==null){
                    return false;
                }
                if (userType.equalsIgnoreCase(config.getUserType())){
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("userType");
    }
}

package com.wkapps.routes;

import com.wkapps.plantuml.PlantUMLGenerator;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlantUMLRouter extends RouteBuilder {

    @Autowired
    PlantUMLGenerator plantUMLGenerator;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("spark-rest")
                .port("9091")
                .enableCORS(true) // <-- Important
                .corsAllowCredentials(true) // <-- Important
                .corsHeaderProperty("Access-Control-Allow-Origin","*")
                .corsHeaderProperty("Access-Con+trol-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
                .bindingMode(RestBindingMode.auto);

        rest("/uml")
                .post()
                .route()
                .to("direct:generateUML");

        from("direct:generateUML")
                .transform()
                .exchange(plantUMLGenerator::generateFromString)
                .setHeader("Content-Type", constant("image/svg"));
    }
}

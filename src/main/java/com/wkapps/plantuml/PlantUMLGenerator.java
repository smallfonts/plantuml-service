package com.wkapps.plantuml;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.core.DiagramDescription;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Component
public class PlantUMLGenerator {

    public String generateFromString(Exchange exchange) {
        String umlContent = exchange.getIn().getBody(String.class);
        SourceStringReader reader = new SourceStringReader(umlContent);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            DiagramDescription diagramDescription = reader.outputImage(out, 0, new FileFormatOption(FileFormat.PNG));
        } catch(Exception e){
            System.out.println("Error");
            return null;
        }

        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

}

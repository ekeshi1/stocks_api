package com.pp.stocks.nasdaq.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class FlexibleBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {


    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        var bigDecimalString = p.getText();
        if( bigDecimalString.contains(",")){
           bigDecimalString =  bigDecimalString.replaceAll(",","");
        }
        bigDecimalString =  bigDecimalString.replaceAll("[^\\d._-]", "");
        return bigDecimalString.length() == 0 ? BigDecimal.ZERO
                : new BigDecimal(bigDecimalString);
    }
}

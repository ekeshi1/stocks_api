package com.pp.stocks.nasdaq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseStatus {
    @JsonProperty("rCode")
    private Integer rCode;
    private String bCodeMessage ;
    private String developerMessage;

}

package com.pp.stocks.nasdaq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pp.stocks.nasdaq.model.HeadersAndRows;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseData {
    private String symbol;
    private NasdaqStock primaryData;
    @JsonProperty("totalrecords")
    private Integer totalRecords ;
    private Integer limit;
    private Integer offset;
    private String date;
    private HeadersAndRows data;
    private Object filters;
}

package com.pp.stocks.nasdaq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NasdaqResponse {

    private ResponseData data;
    private String message;
    private ResponseStatus status;
}

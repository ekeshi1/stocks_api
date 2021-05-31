package com.pp.stocks.nasdaq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HeadersAndRows {
    private Set<NasdaqStock> rows;
}

package com.pp.account;

import lombok.Data;

@Data
public class AssetBuyModel {

    private AmountModel amount;
    private String assetName;
    //todo: support for market orders
}

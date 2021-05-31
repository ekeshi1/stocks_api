package com.pp.config;

import java.math.MathContext;
import java.time.ZoneId;

public class Constants {
    public static final String DEFAULT_AUDITOR = "system";
    public static final ZoneId ZONE_ID = ZoneId.of("UTC");
    public static final MathContext MY_BIG_DECIMAL_CONTEXT = MathContext.DECIMAL32;
}

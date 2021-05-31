package com.pp.security;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class JwtResponse {
    String jwtToken;
}

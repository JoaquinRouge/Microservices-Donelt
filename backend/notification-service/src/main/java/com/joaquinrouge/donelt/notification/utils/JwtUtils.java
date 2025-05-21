package com.joaquinrouge.donelt.notification.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

	@Value("${security.jwt.private.key}")
    private String privateKey;
	
	@Value("${security.jwt.user.generator}")
    private String userGenerator;

    public DecodedJWT validateJwt(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer(userGenerator)
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("No se pudo verificar el token");
        }
    }

    public String getUsername(DecodedJWT token) {
        return token.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT token, String claim) {
        return token.getClaim(claim);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT token) {
        return token.getClaims();
    }
}

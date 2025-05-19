package com.joaquinrouge.donelt.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.donelt.user.model.UserModel;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

	@Value("${security.jwt.private.key}")
	private String privateKey;
	
	@Value("${security.jwt.user.generator}")
	private String userGenerator;
	
	public String generateToken(Authentication auth) {
			
			Algorithm alg = Algorithm.HMAC256(privateKey);
			
			UserModel user = (UserModel) auth.getPrincipal();
			
			return JWT.create()
					.withIssuer(userGenerator)
					.withSubject(user.getUsername())
					.withClaim("id", user.getId())
					.withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + (30 * 60000))) // 30 minutos
					.withJWTId(UUID.randomUUID().toString())
					.withNotBefore(new Date(System.currentTimeMillis()))
					.sign(alg);
		}
	
			
		public DecodedJWT validateJwt(String token) {
				
				try {
					
					Algorithm alg = Algorithm.HMAC256(privateKey);
					
					JWTVerifier verifier =  JWT.require(alg)
							.withIssuer(userGenerator)
							.build();
					
					return verifier.verify(token);
				}catch(JWTVerificationException e) {
					throw new JWTVerificationException("No se pudo verificar el token");
				}
				
			}
		
		public String getUsername(DecodedJWT token) {
			return token.getSubject().toString();
		}
		
		public Claim getSpecificClaim(DecodedJWT token, String claim) {
			return token.getClaim(claim);
		}
		
		public Map<String, Claim> getAllClaims(DecodedJWT token){
			return token.getClaims();
		}
}

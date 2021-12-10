package com.iot.server.application.utils;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

public class RSAUtils {

    public static final String ENCRYPT_ALGORITHM = "RSA";

    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    public static final String EMPTY_STRING = "";

    public static PublicKey getPublicKey(String filename) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }

    public static PrivateKey getPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);
    }

    private static PublicKey getPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String publicKeyContent = new String(bytes);
        publicKeyContent = publicKeyContent
                .replaceAll("\\n*", EMPTY_STRING)
                .replace(BEGIN_PUBLIC_KEY, EMPTY_STRING)
                .replace(END_PUBLIC_KEY, EMPTY_STRING);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));

        KeyFactory factory = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        return factory.generatePublic(spec);
    }

    private static PrivateKey getPrivateKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyContent = new String(bytes);
        privateKeyContent = privateKeyContent
                .replaceAll("\\n*", EMPTY_STRING)
                .replace(BEGIN_PRIVATE_KEY, EMPTY_STRING)
                .replace(END_PRIVATE_KEY, EMPTY_STRING);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));

        KeyFactory factory = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        return factory.generatePrivate(spec);
    }

    private static byte[] readFile(String filename) throws IOException {
        File file = new ClassPathResource(filename).getFile();
        return Files.readAllBytes(file.toPath());
    }

    public static void main(String[] args) throws Exception {
        PrivateKey privateKey = getPrivateKey("private_key_pkcs8.pem");
        PublicKey publicKey = getPublicKey("public_key.pem");

        JWKSet jwk = new JWKSet(
                new RSAKey.Builder((RSAPublicKey) publicKey).privateKey(privateKey).build());

        Claims claims = Jwts.claims()
                .setSubject("UUID");
        claims.put("firstName", "An");

        ZonedDateTime currentTime = ZonedDateTime.now();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer("com.iot")
                .setIssuedAt(Date.from(currentTime.toInstant()))
                .setExpiration(Date.from(currentTime.plusSeconds(60000).toInstant()))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();

        System.out.println(jwk.toJSONObject());
        System.out.println(accessToken);

        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(accessToken);

        System.out.println(claimsJws.getBody());
    }
}
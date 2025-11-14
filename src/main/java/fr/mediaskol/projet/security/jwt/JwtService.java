package fr.mediaskol.projet.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import fr.mediaskol.projet.bo.Utilisateur;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Service
public class JwtService {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {
        privateKey = loadPrivateKey();
        publicKey = loadPublicKey();
    }

    private PrivateKey loadPrivateKey() throws Exception {
        ClassPathResource resource = new ClassPathResource("keys/private.key");
        String key;
        try (InputStream inputStream = resource.getInputStream()) {
            key = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private PublicKey loadPublicKey() throws Exception {
        ClassPathResource resource = new ClassPathResource("keys/public.key");
        String key;
        try (InputStream inputStream = resource.getInputStream()) {
            key = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public String generateToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1h
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    // 🧩 Ajout 1 — extraire le nom d’utilisateur
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 🧩 Ajout 2 — extraire toutes les informations du token (claims)
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

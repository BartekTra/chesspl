package com.example.chesspl.security;

public interface TokenProvider {
    String generateToken(String username);
    String getUserFromToken(String token);
}
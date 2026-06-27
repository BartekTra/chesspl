package com.example.chesspl.service.i18l;

public interface TranslationService {
    public String getMessage(String code);

    public String getMessage(String code, Object... args);
}


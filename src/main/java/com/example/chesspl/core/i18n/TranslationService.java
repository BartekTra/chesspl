package com.example.chesspl.core.i18n;

public interface TranslationService {
    public String getMessage(String code);

    public String getMessage(String code, Object... args);
}


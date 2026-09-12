package com.sharefiles.domain.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallbackUtil {

    private static final int CODE_LENGTH = 9;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final char[] CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public String generateCode() {
        Set<Character> utilizados = new HashSet<>();

        while (utilizados.size() < CODE_LENGTH) {
            char caractere = CARACTERES[SECURE_RANDOM.nextInt(CARACTERES.length)];
            utilizados.add(caractere);
        }

        StringBuilder code = new StringBuilder();

        for (char caractere : utilizados) {
            code.append(caractere);
        }

        return code.toString();
    }
}

package ru.netology.data;

import lombok.Value;

import java.util.UUID;

public class DataHelper {

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo(
                "vasya",
                "qwerty123"
        );
    }
// Генерация случайного неверного пароля
    public static String generateRandomPassword() {
        return UUID.randomUUID().toString();
    }
}


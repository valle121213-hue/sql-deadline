package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.by;

public class LoginPage {

    private final SelenideElement loginField = $(by("data-test-id", "login"));
    private final SelenideElement passwordField = $(by("data-test-id", "password"));
    private final SelenideElement loginButton = $(by("data-test-id", "action-login"));
    private final SelenideElement errorNotification = $(by("data-test-id", "error-notification"));

    public void login(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public String getErrorNotification() {
        return errorNotification.getText();
    }
}

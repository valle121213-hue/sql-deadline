package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Condition.visible;

public class LoginPage {

    private final SelenideElement loginField =
            $(by("data-test-id", "login")).$("input");

    private final SelenideElement passwordField =
            $(by("data-test-id", "password")).$("input");

    private final SelenideElement loginButton =
            $(by("data-test-id", "action-login"));

    private final SelenideElement errorNotification =
            $(by("data-test-id", "error-notification"));

    public void login(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public String getErrorNotification() {
        return errorNotification.getText();
    }

    public void waitForError() {
        errorNotification.shouldBe(visible);
    }
}
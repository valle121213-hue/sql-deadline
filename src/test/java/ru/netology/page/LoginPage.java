package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.by;

public class LoginPage {

    private final SelenideElement loginField =
            $(by("data-test-id", "login")).$("input");

    private final SelenideElement passwordField =
            $(by("data-test-id", "password")).$("input");

    private final SelenideElement loginButton =
            $(by("data-test-id", "action-login"));

    private final SelenideElement errorNotification =
            $(by("data-test-id", "error-notification"))
                    .$(".notification__content");

    public void verifyErrorNotification(String expectedText) {
        errorNotification
                .shouldHave(exactText(expectedText))
                .shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login(info);
        return new VerificationPage();
    }

    private void clearField(SelenideElement field) {
        field.click();
        field.press(Keys.END);
        field.press(Keys.chord(Keys.SHIFT, Keys.HOME));
        field.press(Keys.DELETE);
    }

    public void login(DataHelper.AuthInfo info) {
        // в данном приложении не работает
        // loginField.clear();
        // passwordField.clear();

// Так же не удаляет момент дублирования
        // loginField.setValue("");
        // passwordField.setValue("");

        // loginField.click();
        // loginField.press(Keys.chord(Keys.CONTROL, "A"));
        // loginField.press(Keys.BACK_SPACE);

        // passwordField.click();
        // passwordField.press(Keys.chord(Keys.CONTROL, "A"));
        // passwordField.press(Keys.BACK_SPACE);
        clearField(loginField);
        clearField(passwordField);

        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    // метод для неправильного пароля
    public void login(String login, String password) {
        // в данном приложении не работает
        // loginField.clear();
        // passwordField.clear();

        // Так же не удаляет момент дублирования
        // loginField.setValue("");
        // passwordField.setValue("");

        // loginField.click();
        // loginField.press(Keys.chord(Keys.CONTROL, "A"));
        // loginField.press(Keys.BACK_SPACE);

        // passwordField.click();
        // passwordField.press(Keys.chord(Keys.CONTROL, "A"));
        // passwordField.press(Keys.BACK_SPACE);

        clearField(loginField);
        clearField(passwordField);

        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }
}
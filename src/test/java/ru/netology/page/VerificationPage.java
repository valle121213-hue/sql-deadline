package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.by;

public class VerificationPage {

    private final SelenideElement codeField =
            $(by("data-test-id", "code")).$("input");

    private final SelenideElement verifyButton =
            $(by("data-test-id", "action-verify"));

    private final SelenideElement errorNotification =
            $(by("data-test-id", "error-notification"));

    public void verify(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public String getErrorNotification() {
        return errorNotification.getText();
    }
}

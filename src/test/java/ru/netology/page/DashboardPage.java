package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byText;

public class DashboardPage {

    private final SelenideElement title =
            $(byText("Личный кабинет"));

    public void verifyDashboardVisible() {
        title
                .shouldBe(visible)
                .shouldHave(text("Личный кабинет"));
    }
}

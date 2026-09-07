package ru.netology;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.db.DatabaseHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginTest {
    private LoginPage loginPage;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseHelper.setUserActive(
                DataHelper.getAuthInfo().getLogin()
        );

        loginPage = open(
                "http://localhost:9999",
                LoginPage.class
        );
    }

    @AfterAll
    static void cleanDatabase() throws SQLException {
        DatabaseHelper.cleanDatabase();
    }

    @Test
    void shouldLoginSuccessfully() throws SQLException {
        var authInfo = DataHelper.getAuthInfo();

        var verificationPage = loginPage.validLogin(authInfo);

        var verificationCode =
                DatabaseHelper.getVerificationCode(authInfo.getLogin());

        assertNotNull(
                verificationCode,
                "Код подтверждения не найден в базе данных"
        );

        var dashboardPage =
                verificationPage.validVerify(verificationCode);

        dashboardPage.verifyDashboardVisible();
    }

    @Test
    void shouldBlockUserAfterThreeInvalidPasswords() throws SQLException {
        var authInfo = DataHelper.getAuthInfo();
        var wrongPassword = DataHelper.generateRandomPassword();

        loginPage.login(authInfo.getLogin(), wrongPassword);
        loginPage.verifyErrorNotification(
                "Ошибка! Неверно указан логин или пароль"
        );

        loginPage.login(authInfo.getLogin(), wrongPassword);
        loginPage.verifyErrorNotification(
                "Ошибка! Неверно указан логин или пароль"
        );

        loginPage.login(authInfo.getLogin(), wrongPassword);
        loginPage.verifyErrorNotification(
                "Ошибка! Неверно указан логин или пароль"
        );

        var status =
                DatabaseHelper.getUserStatus(authInfo.getLogin());

        assertEquals(
                "blocked",
                status,
                "Фактический статус пользователя: " + status
        );
    }
}
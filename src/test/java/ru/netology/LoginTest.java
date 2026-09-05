package ru.netology;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.db.DatabaseHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;
import ru.netology.page.DashboardPage;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginTest {

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseHelper.setUserActive(DataHelper.getAuthInfo().getLogin());
    }

    @AfterAll
    static void cleanDatabase() throws SQLException {
        DatabaseHelper.cleanDatabase();
    }

    @Test
    void shouldLoginSuccessfully() throws SQLException {
        var authInfo = DataHelper.getAuthInfo();

        open("http://localhost:9999");

        var loginPage = new LoginPage();
        loginPage.login(authInfo.getLogin(), authInfo.getPassword());

        var verificationCode =
                DatabaseHelper.getVerificationCode(authInfo.getLogin());

        assertNotNull(verificationCode);

        var verificationPage = new VerificationPage();
        verificationPage.verify(verificationCode);

        var dashboardPage = new DashboardPage();
        dashboardPage.verifyDashboardVisible();

    }


    @Test
    void shouldBlockUserAfterThreeInvalidPasswords() throws SQLException {
        var authInfo = DataHelper.getAuthInfo();
        var wrongPassword = DataHelper.generateRandomPassword();

        open("http://localhost:9999");

        var loginPage = new LoginPage();

        System.out.println("До попыток: " +
                DatabaseHelper.getUserStatus(authInfo.getLogin()));

        // Первая неправильная попытка
        loginPage.login(authInfo.getLogin(), wrongPassword);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

        // Вторая неправильная попытка
        loginPage.login(authInfo.getLogin(), wrongPassword);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

        // Третья неправильная попытка
        loginPage.login(authInfo.getLogin(), wrongPassword);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

        var status = DatabaseHelper.getUserStatus(authInfo.getLogin());

        assertEquals(
                "blocked",
                status,
                "Фактический статус пользователя: " + status
        );
    }

}
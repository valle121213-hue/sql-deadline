package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.db.DatabaseHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginTest {

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

        Selenide.sleep(1000);
    }

    @Test
    void shouldBlockUserAfterThreeInvalidPasswords() throws SQLException {
        var authInfo = DataHelper.getAuthInfo();

        open("http://localhost:9999");

        var loginPage = new LoginPage();

        // Первая неправильная попытка
        loginPage.login(authInfo.getLogin(), "wrongPassword1");
        loginPage.waitForError();

        // Вторая неправильная попытка
        loginPage.login(authInfo.getLogin(), "wrongPassword2");
        loginPage.waitForError();

        // Третья неправильная попытка
        loginPage.login(authInfo.getLogin(), "wrongPassword3");
        loginPage.waitForError();

        // После трёх неправильных паролей пользователь должен быть заблокирован
        String status = DatabaseHelper.getUserStatus(authInfo.getLogin());

        assertEquals("blocked", status);
    }
}
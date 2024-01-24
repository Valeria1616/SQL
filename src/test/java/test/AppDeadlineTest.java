package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import data.DataHelp;
import data.SQLHelp;
import page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;

public class AppDeadlineTest {

    @AfterAll
    static void tearDownAll() {
        //cleanDatabase();
    }

    @Test
    void successLogin() {
        var login = open("http://localhost:9999", LoginPageV1.class);
        var authInfo = DataHelp.getAuthInfo();
        var verification = login.validLogin(authInfo);
        verification.verificationPageVisible();
        var verifCode = SQLHelp.getVerifCode();
        verification.validVerify(verifCode.getCode());
    }

    @Test
    void invalidVerificationCode() {
        var login = open("http://localhost:9999", LoginPageV1.class);
        var authInfo = DataHelp.getAuthInfo();
        var verification = login.validLogin(authInfo);
        verification.verificationPageVisible();
        var verifCode = DataHelp.getInvalidVerificationCodeFor();
        verification.verify(verifCode.getCode());
        verification.verifyCodeErrorVisible();
    }

}
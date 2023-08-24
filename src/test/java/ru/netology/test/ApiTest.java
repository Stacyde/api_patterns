package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.Generator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ApiTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }
    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfullyLoginWithActiveRegisteredUser() {
        val user = Generator.Registration.generateUser("us","active");
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should notify login with blocked registered user")
    void shouldNotifyLoginWithBlockedRegisteredUser() {
        val user = Generator.Registration.generateUser("us", "blocked");
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Пользователь заблокирован")).shouldBe((Condition.visible));;
    }
    @Test
    @DisplayName("Should notify error login")
    void shouldNotifyErrorLogin() {
        val user = Generator.Registration.generateUser("us","active");
        $("span[data-test-id='login'] input").setValue("");
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $(".input_invalid")
                .shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe((Condition.visible));;
    }
    @Test
    @DisplayName("Should notify error password")
    void shouldNotifyErrorPassword() {
        val user = Generator.Registration.generateUser("us","active");
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue("");
        $(byText("Продолжить")).click();
        $(".input_invalid")
                .shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe((Condition.visible));;
    }
    @Test
    @DisplayName("Should invalid error login")
    void shouldInvalidErrorLogin() {
        val user = Generator.Registration.generateUser("us","active");
        $("span[data-test-id='login'] input").setValue(user.getLogin() + "!");
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification' ] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe((Condition.visible));;
    }
    @Test
    @DisplayName("Should invalid error password")
    void shouldInvalidErrorPassword() {
        val user = Generator.Registration.generateUser("us","active");
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword() + "$$$");
        $(byText("Продолжить")).click();
        $("div[data-test-id='error-notification']")
                .shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe((Condition.visible));;
    }

}

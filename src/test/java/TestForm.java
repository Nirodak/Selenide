import com.codeborne.selenide.*;
import com.codeborne.selenide.conditions.TextCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import org.openqa.selenium.By;


public class TestForm {
    SelenideElement element;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");

    }

    private void enterValue(String name, String number) {
        element = $(By.className("App_appContainer__3jRx1"));
        element.$("[name='name']").setValue(name);
        element.$("[name='phone']").setValue(number);
        element.$("[class='checkbox__text']").click();
        element.$("[class='button__text']").click();

    }

    @Test
    void TestValid() {
        enterValue("иванов иван иванович", "+79184564545");
        $(withText("Ваша заявка успешно отправлена!")).shouldBe(visible);
    }

    @Test
    void TestInvalidName() {
        enterValue("ivan", "+79546548484");
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(visible);
    }

    @Test
    void TestInvalidPhone() {
        enterValue("Петр Сергеевич", "plus sem");
        element.$$(".input__sub").last().shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например," +
                " +79012345678."));
    }

    @Test
    void TestCheckBox() {
        $("[name='name']").setValue("Иванов Иван Иванович");
        $("[name='phone']").setValue("+79181181818");
        $("[class='button__text']").click();
        $(".input_invalid[data-test-id='agreement").shouldBe(visible);

    }
}
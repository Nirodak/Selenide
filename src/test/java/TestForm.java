import com.codeborne.selenide.*;
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
        element = $("form");
        element.$("[name='name']").setValue(name);
        element.$("[name='phone']").setValue(number);
        element.$("[class='checkbox__text']").click();
        element.$("[class='button__text']").click();

    }

    @Test
    void testValid() {
        enterValue("иванов иван иванович", "+79184564545");
        $(withText("Ваша заявка успешно отправлена!")).shouldBe(visible);


    }

    @Test
    void testInvalidName() {
        enterValue("ivan", "+79546548484");
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно." +
                " Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void testInvalidPhone() {
        enterValue("Петр Сергеевич", "plus sem");
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например," +
                " +79012345678."));

    }

    @Test
    void testCheckBox() {
        $("[name='name']").setValue("Иванов Иван Иванович");
        $("[name='phone']").setValue("+79181181818");
        $("[class='button__text']").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible);

    }
    @Test
    void nullValues(){
        $("[class='button__text']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }
}

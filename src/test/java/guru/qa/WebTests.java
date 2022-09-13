package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class WebTests {

    @Disabled
    @ValueSource(strings = {"Selenide" , "Allure"})
    @ParameterizedTest(name = "GitHub search results have \"{0}\" as top result.")
    void simpleGitHubSearchTest(String testData) {

        open("https://github.com/");
        $("[data-test-selector=nav-search-input]").setValue(testData).pressEnter();
        $("ul.repo-list li").shouldHave(text(testData));

    }

    @Disabled
    @CsvSource(value = {
            "Allure, Allure Report is a flexible",
            "Selenide, Concise UI Tests with Java!"
    })
    @ParameterizedTest(name = "GitHub search results have \"{0}\" as top result with text \"{1}\".")
    void complexGitHubSearchTest(String testData, String expectedResult) {

        open("https://github.com/");
        $("[data-test-selector=nav-search-input]").setValue(testData).pressEnter();
        $("ul.repo-list li").shouldHave(text(expectedResult));

    }

    static Stream<Arguments> gitHubPricingTest() {
        return Stream.of(
                Arguments.of(Cycle.Monthly, List.of("0", "4", "21")),
                Arguments.of(Cycle.Yearly, List.of("0", "44", "231"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Pricing for {0} plan should have prices of {1}")
   void gitHubPricingTest(Cycle cycle, List<String> expectedPrice) {
        open("https://github.com/pricing");
        $$(".d-inline-block label").find(text(cycle.name())).click();
        $$(".js-pricing-plan").shouldHave(CollectionCondition.texts(expectedPrice));
    }
}

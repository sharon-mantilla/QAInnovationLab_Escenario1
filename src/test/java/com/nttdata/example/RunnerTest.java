package com.nttdata.example;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/store",
        glue = {"com.nttdata.example"},
        plugin = {"pretty", "json:target/cucumber.json"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        monochrome = true
)
public class RunnerTest {
}

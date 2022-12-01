package it.januszwisniowski.stackoverflow.springbootvalidationtestdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DemoConfigurationPropertiesTest {
    @Configuration
    @EnableConfigurationProperties(DemoConfigurationProperties.class)
    static class TestConfig {}

    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setup() {
        applicationContextRunner = new ApplicationContextRunner().withUserConfiguration(TestConfig.class);
    }

    @Test
    void invalidPropertyCausesException() {
        Throwable thrownWhenPropertyValidationFails = catchThrowable(() ->
                applicationContextRunner.run(ctx -> ctx.getBean(DemoConfigurationProperties.class)));

        assertThat(thrownWhenPropertyValidationFails).hasRootCauseInstanceOf(BindValidationException.class);
    }

    @Test
    void validPropertyBinds() {
        String expectedPropertyValue = "some valid value";

        applicationContextRunner
                .withPropertyValues("some-prefix.some-property=" + expectedPropertyValue)
                .run(ctx -> {
                    DemoConfigurationProperties actualDemoConfigurationProperties = ctx.getBean(DemoConfigurationProperties.class);

                    assertThat(actualDemoConfigurationProperties)
                            .extracting(DemoConfigurationProperties::getSomeProperty)
                            .isEqualTo(expectedPropertyValue);
                });
    }
}

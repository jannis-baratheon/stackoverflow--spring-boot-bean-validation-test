package it.januszwisniowski.stackoverflow.springbootvalidationtestdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties("some-prefix")
@ConstructorBinding
@Validated
public class DemoConfigurationProperties {
    @NotEmpty
    private final String someProperty;

    public DemoConfigurationProperties(String someProperty) {
        this.someProperty = someProperty;
    }

    public String getSomeProperty() {
        return someProperty;
    }
}

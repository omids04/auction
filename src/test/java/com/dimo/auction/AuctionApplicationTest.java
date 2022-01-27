package com.dimo.auction;

import io.cucumber.core.options.Constants;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CucumberContextConfiguration
@Suite
@SelectClasspathResource("com/dimo/auction/application/usecases")
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "com/dimo/auction"
)
class AuctionApplicationTest {

}

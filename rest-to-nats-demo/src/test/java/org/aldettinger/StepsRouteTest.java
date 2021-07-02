package org.aldettinger;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class StepsRouteTest {

  @InjectMock
  Step2Bean step2Bean;

  @Test
  void step2BeanShouldBeMocked() {
    when(step2Bean.step(anyString())).thenReturn("STEP 1 - mocked");
    given().get("/steps").then().statusCode(200).body(is("STEP 1"));
  }

}
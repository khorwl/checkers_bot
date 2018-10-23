package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import tools.QueryParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueryParserUnitTests {

  private QueryParser parser;

  @BeforeEach
  public void setUp() {
    parser = new QueryParser();
  }

  @Test
  public void parseToMap_emptyString_shouldReturnEmptyMap() {
    assertEquals(0, parser.parseToMap("").size());
  }

  @Test
  public void parseToMap_nullString_shouldReturnEmptyMap() {
    assertEquals(0, parser.parseToMap(null).size());
  }

  @Test
  public void parseToMap_allKeysExists_shouldReturnRightMap() {
    var expected = new HashMap<String, String>();
    expected.put("lol", "lal");
    expected.put("key", "value");
    expected.put("i_love", "java");

    var sut = parser.parseToMap("lol=lal&key=value&i_love=java");

    assertEquals(expected, sut);
  }

  @Test
  public void parseToMap_keysWithoutValue_shouldReturnRightMap() {
    var expected = new HashMap<String, String>();
    expected.put("lol", "lal");
    expected.put("key", "");
    expected.put("i_love", "");

    var sut = parser.parseToMap("lol=lal&key=&i_love");

    assertEquals(expected, sut);
  }
}

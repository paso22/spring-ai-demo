package ge.paso22.springaidemo.tool;

import java.time.LocalDateTime;
import org.springframework.ai.tool.annotation.Tool;

public class DateTools {

  @Tool(description = "returns current date")
  public String getCurrentDate() {
    return LocalDateTime.now().toString();
  }
}

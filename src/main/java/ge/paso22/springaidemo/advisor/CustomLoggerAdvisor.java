package ge.paso22.springaidemo.advisor;

import io.micrometer.common.lang.NonNullApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

@Slf4j
@NonNullApi
public class CustomLoggerAdvisor implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(
      ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
    log.info("request : {}", chatClientRequest.toString());
    ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
    log.info("response : {}", chatClientResponse);

    return chatClientResponse;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public int getOrder() {
    return 0;
  }
}

package ge.paso22.springaidemo.controller;

import ge.paso22.springaidemo.domain.Player;
import ge.paso22.springaidemo.tool.DateTools;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatControllerImpl implements ChatController {

  private final ChatClient chatClient;

  private final VectorStore vectorStore;

  @Value("classpath:/data/bookStore.json")
  private Resource bookStoreResource;

  @Override
  public String plainChat(String message) {
    return chatClient.prompt().user(message).call().content();
  }

  @Override
  public Flux<String> plainChatWithStream(String message) {
    return chatClient.prompt().user(message).stream().content();
  }

  @Override
  public String chatWithMemory(String message) {
    return chatClient.prompt().user(message).call().content();
  }

  @Override
  public List<Player> structuredOutput(String message) {
    return chatClient.prompt().user(message).call().entity(new ParameterizedTypeReference<>() {});
  }

  @Override
  public String promptStuffing(String message) {
    var systemPrompt =
        """
          You're a book store manager, who only answers questions about books.
          If anyone asks you different question, please answer in this format - "I'm only able to give you information about books in our store!".
          Be polite, try to be as precise as you can.
          If anyone asks you about Russian authors, for example Fyodor Dostoevsky or Mikhail Bulgakov, answer them with given text :
          "We do not have books of Russian authors. Fuck Russia and have a good day!"
        """;
    return chatClient
        .prompt()
        .system(systemPrompt)
        .user(
            u ->
                u.text(message + " \n {additionalContext}")
                    .param("additionalContext", asString(bookStoreResource)))
        .call()
        .content();
  }

  @Override
  public String toolCalling(String message) {
    return chatClient.prompt().tools(new DateTools()).user(message).call().content();
  }

  @Override
  public String mcp(String message) {
    return "";
  }

  @Override
  public String rag(String message) {
    return chatClient
        .prompt()
        .advisors(new QuestionAnswerAdvisor(vectorStore))
        .user(message)
        .call()
        .content();
  }

  private String asString(Resource bookStoreResource) {
    try {
      return bookStoreResource.getContentAsString(Charset.defaultCharset());
    } catch (IOException e) {
      log.error("error occurred during getting book store json content as a string", e);
      return "";
    }
  }
}

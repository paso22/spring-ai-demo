package ge.paso22.springaidemo.config;

import ge.paso22.springaidemo.advisor.CustomLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfiguration {

  @Bean
  Advisor customLoggerAdvisor() {
    return new CustomLoggerAdvisor();
  }

  @Bean
  Advisor memoryAdvisor(ChatMemory chatMemory) {
    return MessageChatMemoryAdvisor.builder(chatMemory).build();
  }

  /** Custom Logger Advisor * */
  @Bean
  ChatClient chatClientWithCustomLoggerAdvisor(ChatClient.Builder builder) {
    return builder.defaultAdvisors(customLoggerAdvisor()).build();
  }

  /** Default Logger Advisor * */
  //  @Bean
  //  ChatClient chatClientWithDefaultLoggerAdvisor(ChatClient.Builder builder) {
  //    return builder.defaultAdvisors(SimpleLoggerAdvisor.builder().build()).build();
  //  }

  /** Memory advisor * */
  //  @Bean
  //  ChatClient chatClientWithCustomLoggerAndMemoryAdvisor(
  //      ChatClient.Builder builder, ChatMemory chatMemory) {
  //    return builder.defaultAdvisors(customLoggerAdvisor(), memoryAdvisor(chatMemory)).build();
  //  }
}

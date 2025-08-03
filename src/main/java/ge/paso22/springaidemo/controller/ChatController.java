package ge.paso22.springaidemo.controller;

import ge.paso22.springaidemo.domain.Player;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Tag(name = "Chat")
@RequestMapping("api/v1/chat")
public interface ChatController {

  @GetMapping("/plain")
  String plainChat(@RequestParam String message);

  @GetMapping("/plain-stream")
  Flux<String> plainChatWithStream(@RequestParam String message);

  @GetMapping("/memory")
  String chatWithMemory(String message);

  @GetMapping("/structured-output")
  List<Player> structuredOutput(@RequestParam String message);

  @GetMapping("/prompt-stuffing")
  String promptStuffing(@RequestParam String message);

  @GetMapping("/tool-calling")
  String toolCalling(@RequestParam(defaultValue = "What is current date?") String message);

  @GetMapping("/mcp")
  String mcp(@RequestParam String message);

  @GetMapping("/rag")
  String rag(@RequestParam String message);
}

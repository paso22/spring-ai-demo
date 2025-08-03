package ge.paso22.springaidemo.config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class RagConfiguration {

  @Value("classpath:/data/topMovies.json")
  private Resource topMoviesResource;

  private final String vectorStoreName = "vectorStore.json";

  @Bean
  public SimpleVectorStore vectorStore(EmbeddingModel embeddingModel) {
    SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
    File vectorStore = getVectorStore();
    if (!vectorStore.exists()) {
      TextReader textReader = new TextReader(topMoviesResource);
      List<Document> documents = textReader.get();
      TokenTextSplitter textSplitter = new TokenTextSplitter();
      List<Document> splitDocuments = textSplitter.split(documents);
      simpleVectorStore.accept(splitDocuments);
      simpleVectorStore.save(vectorStore);
    } else {
      simpleVectorStore.load(vectorStore);
    }

    return simpleVectorStore;
  }

  private File getVectorStore() {
    Path path = Paths.get("src", "main", "resources", "data");
    String absolutePath = path.toFile().getAbsolutePath() + "/" + vectorStoreName;
    return new File(absolutePath);
  }
}

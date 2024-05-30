package guru.ysy.aiexpert.bootstrap;

import guru.ysy.aiexpert.config.VectorStoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/24 22:22
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@Component
public class LoadVectorStore implements CommandLineRunner {

    private final VectorStore vectorStore;
    private final VectorStoreProperties vectorStoreProperties;

    @Autowired
    public LoadVectorStore(VectorStore vectorStore, VectorStoreProperties vectorStoreProperties) {
        this.vectorStore = vectorStore;
        this.vectorStoreProperties = vectorStoreProperties;
    }

    @Override
    public void run(String... args) {
        log.debug("Loading vector store...");
        if (vectorStore.similaritySearch("Sportsman").isEmpty()) {
            log.debug("Loading documents into vector store...");
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.debug("Loading document: {}", document.getFilename());
                // use tika to load document
                TikaDocumentReader tikaReader = new TikaDocumentReader(document);
                List<Document> docs = tikaReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);
                // add to vector store
                vectorStore.add(splitDocs);
            });
        }
        log.info("Vector store loaded.");
    }

}

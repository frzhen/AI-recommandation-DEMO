package guru.ysy.aiexpert.services;

import guru.ysy.aiexpert.models.Answer;
import guru.ysy.aiexpert.models.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/24 21:48
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MistralAiServiceImpl implements MistralAiService {

    private final ChatClient chatClient;

    private final VectorStore vectorStore;

    @Value("classpath:/templates/rag-prompt-template-meta.st")
    private Resource ragPromptTemplate;

    @Override
    public Answer getAnswer(Question question) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.query(question.question()).withTopK(4));
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "input", question.question(),
                "documents", String.join("\n", contentList)
        ));
        contentList.forEach(log::info);
        ChatResponse response = chatClient.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }
}

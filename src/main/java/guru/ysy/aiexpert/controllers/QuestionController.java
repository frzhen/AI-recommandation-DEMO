package guru.ysy.aiexpert.controllers;

import guru.ysy.aiexpert.models.Answer;
import guru.ysy.aiexpert.models.Question;
import guru.ysy.aiexpert.services.AiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/24 21:57
 * @Email: fred.zhen@gmail.com
 */
@RequiredArgsConstructor
@RestController
@Tag(name = "Question Controller", description = "Endpoints for Question to Mistral AI")
public class QuestionController {
    private final AiService aiService;

    @PostMapping("/ask")
    public Answer question(Question question) {
        return aiService.getAnswer(question);
    }
}

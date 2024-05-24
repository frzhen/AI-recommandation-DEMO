package guru.ysy.aiexpert.services;

import guru.ysy.aiexpert.models.Answer;
import guru.ysy.aiexpert.models.Question;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/24 21:47
 * @Email: fred.zhen@gmail.com
 */
public interface MistralAiService {

    Answer getAnswer(Question question);
}

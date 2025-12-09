import java.util.*;

public class QuizCore {

    public static class Question {
        public String dish;
        public String correctCountry;
        public List<String> wrongCountries;
        public String imagePath;

        public Question(String dish, String correctCountry,
                        List<String> wrongCountries, String imagePath) {
            this.dish = dish;
            this.correctCountry = correctCountry;
            this.wrongCountries = wrongCountries;
            this.imagePath = imagePath;
        }
    }

    private final List<Question> questions;
    private int index = 0;
    private int score = 0;

    public QuizCore(List<Question> questions) {
        this.questions = questions;
    }

    public Question getCurrentQuestion() {
        if (index >= questions.size()) return null;
        return questions.get(index);
    }

    public List<String> getShuffledAnswers() {
        Question q = getCurrentQuestion();
        List<String> answers = new ArrayList<>(q.wrongCountries);
        answers.add(q.correctCountry);
        Collections.shuffle(answers);
        return answers;
    }

    public boolean answer(String selected) {
        Question q = getCurrentQuestion();

        boolean correct;
        if (selected.equals(q.correctCountry)) {
            score += 5;
            correct = true;
        } else {
            correct = false;
        }

        index++;
        return correct;
    }

    public int getScore() {
        return score;
    }

    public boolean isFinished() {
        return index >= questions.size();
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}

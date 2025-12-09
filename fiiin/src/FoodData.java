import java.util.List;

public class FoodData {

    public static List<QuizCore.Question> getQuestions() {

        return List.of(

                new QuizCore.Question(
                        "Суши",
                        "Япония",
                        List.of("Италия", "Китай", "Франция"),
                        "images/sushi.jpg"
                ),

                new QuizCore.Question(
                        "Пицца",
                        "Италия",
                        List.of("США", "Германия", "Франция"),
                        "images/pizza.jpg"
                )

                // добавляете дальше
        );
    }
}

package animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

public class Animal {
    final public String article;
    final public String name;

    public Animal(String article, String name) {
        this.article = article;
        this.name = name;
    }

    public String nameWithArticle() {
        return article + " " + name;
    }

    @Override
    public String toString() {
        return "Animal{" + "'" + article + " " + name + "'}";
    }

    public static void main(String[] args) throws IOException {
        Animal animal = new Animal("a", "cat");
        String fileName = "animal.json";
        ObjectMapper objectMapper = new JsonMapper();

        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(fileName), animal);
    }
}

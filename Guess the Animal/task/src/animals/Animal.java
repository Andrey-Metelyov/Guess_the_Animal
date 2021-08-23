package animals;

public class Animal {
    public String article;
    public String name;

    public Animal() { }
    public Animal(String article, String name) {
        this.article = article;
        this.name = name;
    }

    public String nameWithArticle() {
        return article + " " + name;
    }

    @Override
    public String toString() {
        return "Animal{" + "'" + article + "' '" + name + "'}";
    }

//    public static void main(String[] args) throws IOException {
//        Animal animal = new Animal("a", "cat");
//        String fileName = "animal.json";
//        ObjectMapper objectMapper = new JsonMapper();
//
//        objectMapper
//                .writerWithDefaultPrettyPrinter()
//                .writeValue(new File(fileName), animal);
//    }
}

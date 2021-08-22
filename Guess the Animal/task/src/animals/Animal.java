package animals;

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
}

package blockbuster.menu;

@FunctionalInterface
public interface OptionMenu<T> {
    void invocar(T context);
}
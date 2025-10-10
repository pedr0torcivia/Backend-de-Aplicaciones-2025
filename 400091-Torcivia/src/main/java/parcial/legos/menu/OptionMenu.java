package parcial.legos.menu;

@FunctionalInterface
public interface OptionMenu<T> {
    void invocar(T context);
}
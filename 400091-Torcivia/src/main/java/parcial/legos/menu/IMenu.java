package parcial.legos.menu;

public interface IMenu<T> {
    void addOption(int opcion, ItemMenu<T> action);
    void runMenu(T context);
}
package enunciado.parcial.services.interfaces;
import java.util.List;
import java.util.stream.Stream;

public interface IService <T, K>{
    T getById(K id);
    T getOrCreateByName(String name);
    List<T> getAll();
    Stream<T> getAllStream();
}

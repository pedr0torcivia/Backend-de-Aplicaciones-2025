package parcial.legos.services.interfaces;
import java.util.List;
import java.util.stream.Stream;

public interface IService <T, K>{
    T getById(K id);
    List<T> getAll();
    Stream<T> getAllStream();
}

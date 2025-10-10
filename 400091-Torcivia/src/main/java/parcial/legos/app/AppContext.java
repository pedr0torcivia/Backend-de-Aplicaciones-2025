package parcial.legos.app;

import java.util.concurrent.ConcurrentHashMap;

public class AppContext {

    private final ConcurrentHashMap<String, Object> contextMap;

    private AppContext() {
        contextMap = new ConcurrentHashMap<>();
    }

    // Singleton (lazy holder)
    private static class Holder {
        private static final AppContext INSTANCE = new AppContext();
    }

    public static AppContext getInstance() {
        return Holder.INSTANCE;
    }

    // Key-Value genérico
    public void put(String key, Object value) {
        contextMap.put(key, value);
    }

    public Object get(String key) {
        return contextMap.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(contextMap.get(key));
    }

    public void remove(String key) {
        contextMap.remove(key);
    }

    public boolean contains(String key) {
        return contextMap.containsKey(key);
    }

    public void set(String key, Object newValue) {
        if (contextMap.containsKey(key)) {
            contextMap.put(key, newValue);
        } else {
            throw new IllegalArgumentException("La clave no existe en el contexto.");
        }
    }

    // Registro/obtención de servicios por tipo
    public <S> void registerService(Class<S> serviceClass, S service) {
        contextMap.put(serviceClass.getName(), service);
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass) {
        return (S) contextMap.get(serviceClass.getName());
    }
}

package ar.edu.utnfrc.backend.menu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ApplicationContext {

    // Mapa thread-safe para almacenar los objetos compartidos
    private final Map<String, Object> context = new ConcurrentHashMap<>();

    // Constructor privado para evitar instanciación externa
    private ApplicationContext() {}

    // Lazy Holder: garantiza inicialización diferida y thread-safe
    private static class Holder {
        private static final ApplicationContext INSTANCE = new ApplicationContext();
    }

    // Punto de acceso único al contexto. ACA APLICO SINGLETON
    public static ApplicationContext getInstance() {
        return Holder.INSTANCE;
    }

    // --- API ---
    /**
    * Inserta o actualiza un valor asociado a la clave.
    */
    public void put(String key, Object value) {
        context.put(key, value);
    }

    /**
    * Recupera el valor bruto (puede ser null si no existe).
    */
    public Object get(String key) {
        return context.get(key);
    }

    /**
    * Recupera el valor y lo castea al tipo indicado.
    * Lanza ClassCastException si no coincide.
    */
    public <T> T get(String key, Class<T> type) {
        Object value = context.get(key);
        if (value == null) {
            return null;
        }
        return type.cast(value);
    }

    /**
    * Elimina la clave del contexto.
    */
    public void remove(String key) {
        context.remove(key);
    }

    /**
    * Verifica si la clave existe en el contexto.
    */
    public boolean contains(String key) {
        return context.containsKey(key);
    }

    /**
    * Reemplaza un valor existente.
    * Si la clave no existe, lanza IllegalStateException.
    */
    public void set(String key, Object newValue) {
        if (!context.containsKey(key)) {
            throw new IllegalStateException("No existe la clave: " + key);
        }
        context.put(key, newValue);
    }
}

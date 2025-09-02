    public class Main {
        public static void main (String[] args) {
            Mascota miMascota = new Mascota("Pepe", 3, 2);
                    // Mostrar su estado inicial
                System.out.println(miMascota);

                // Hacer que coma
                miMascota.comer();
                System.out.println(miMascota);

                // Hacer que corra
                miMascota.correr();
                System.out.println(miMascota);

                // Hacer que duerma
                miMascota.dormir();
                System.out.println(miMascota);

                // Despertarla
                miMascota.despertar();
                System.out.println(miMascota);
                
                miMascota.correr();

                miMascota.comer();
                miMascota.comer();
                miMascota.comer();
                System.out.println(miMascota);
                miMascota.correr();
                System.out.println(miMascota);
                miMascota.comer();
                miMascota.comer();
                System.out.println(miMascota);
                miMascota.comer();
                System.out.println(miMascota);
                miMascota.comer();
                miMascota.comer();
                miMascota.comer();
                System.out.println(miMascota);

        }
    }


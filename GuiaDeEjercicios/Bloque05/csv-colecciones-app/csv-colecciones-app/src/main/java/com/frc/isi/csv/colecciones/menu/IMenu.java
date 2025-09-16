package com.frc.isi.csv.colecciones.menu;

import java.util.Scanner;

public interface IMenu<T> {
    void registrarOpcion(Integer indice, String textoAMostrar, OpcionDeMenu<T> action);
    void invocarAction(T contexto, Scanner lector);
}

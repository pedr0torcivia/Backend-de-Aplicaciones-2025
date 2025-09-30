package ar.edu.utnfrc.backend.menu;

// una opcion del menu tiene un numero (1,2), Un nombre (Hacer tal cosa) y una accion (lo que dispara esa cosa a hacer)
public record MenuOption(int code, String label, FuncAction action) { }

//Func Action es la funcion disparadora


package org.example;

import org.example.controllers.ClienteController;
import org.example.entities.Cliente;
import org.example.persistence.ConfigJPA;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "src/main/resources/logging.properties");
        ClienteController clienteController = new ClienteController();
        Scanner input = new Scanner(System.in);
        List<Cliente> clientes = new LinkedList<>();
        int opcion;
        boolean salir = false;

        bienvenidaAlCrud();

        do {
            mostrarMenu();
            try {
                opcion = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println(" ⚠\uFE0F Opción inválida ⚠\uFE0F ");
                System.out.println("");
                input.nextLine(); // Limpiamos el buffer incorrecto
                continue;
            }
            switch (opcion) {
                case 0:
                    switchSalir();
                    salir = true;
                    continue;
                case 1:
                    switchAgregar(input, clienteController);
                    break;

                case 2:
                    switchListar(clienteController);
                    break;

                case 3:
                    switchActualizar(input, clienteController);
                    break;

                case 4:
                    switchEliminar(input, clienteController);
                    break;

                case 5:
                    switchBuscarPorCiudad(input, clienteController);
                    break;
                default:
                    System.out.println("El número seleccionado no corresponde a ninguna opción.");
            }
        } while (!salir);

        ConfigJPA.close();
        input.close();
    }

    private static void bienvenidaAlCrud() {
        System.out.println("_____________________________________________");
        System.out.println("➡\uFE0F Bienvenid@ a HACK A BOSS CLIENTS CRUD ⬅\uFE0F");
        System.out.println("\uD83D\uDD25 El mejor CRUD de clientes del mercado \uD83D\uDD25");
        System.out.println("_____________________________________________");
        System.out.println("");
    }

    private static void mostrarMenu() {
        System.out.println("______________________________");
        System.out.println("       CRUD DE CLIENTES     ");
        System.out.println("______________________________");
        System.out.println("0 - Salir \uD83D\uDD1A ");
        System.out.println("1 - Agregar un nuevo cliente ➕");
        System.out.println("2 - Listar todos los clientes \uD83D\uDCCB");
        System.out.println("3 - Actualizar información de un cliente ✏\uFE0F");
        System.out.println("4 - Eliminar un cliente \uD83D\uDDD1\uFE0F");
        System.out.println("5 - Buscar cliente por ciudad \uD83D\uDD0D");
        System.out.println("");
        System.out.println("\uD83D\uDC47 Inserte su selección \uD83D\uDC47");
    }

    private static void mostrarErrorFecha() {
        System.out.println("⚠\uFE0F Formato de fecha inválido ⚠\uFE0F");
        System.out.println("El formato correcto es: dd-MM-yyyy");
        System.out.println("Vuelva a escribir la fecha correctamente:");
    }

    private static void switchSalir() {
        System.out.println("**** SALIR ****");
        System.out.println("¡Gracias por usar nuestra app. ¡Hasta pronto!");
    }

    private static void switchAgregar(Scanner input, ClienteController clienteController) {
        System.out.println("**** AGREGAR NUEVO CLIENTE ****");
        Cliente nuevoCliente = new Cliente();
        String nombre;
        String fechaStr;
        do {
            System.out.println("Nombre: ");
            nombre = input.nextLine().trim();
            if (nombre.isBlank()) {
                System.out.println("Debe ingresar un nombre. Vuelva a intentarlo.");
            }
        } while (nombre.isBlank());
        nuevoCliente.setNombre(nombre);
        String apellidos;
        do {
            System.out.println("Apellidos: ");
            apellidos = input.nextLine().trim();
            if (apellidos.isBlank()) {
                System.out.println("Debe ingresar el/los apellidos. Vuelva a intentarlo");
            }
        } while (apellidos.isBlank());
        nuevoCliente.setApellidos(apellidos);
        String sexo = "";
        boolean sexoValido = false;
        do {
            System.out.println("Sexo:");
            System.out.println("1 - Hombre");
            System.out.println("2 - Mujer");
            System.out.println("3 - No especificado");
            System.out.println("Seleccione una opción (1, 2 o 3)");

            String opcionSexo = input.nextLine().trim();
            switch (opcionSexo) {
                case "1":
                    sexo = "Hombre";
                    sexoValido = true;
                    break;
                case "2":
                    sexo = "Mujer";
                    sexoValido = true;
                    break;
                case "3":
                    sexo = "No especificado";
                    sexoValido = true;
                    break;
                default:
                    System.out.println("Opción inválida, seleccione una opción entre 1, 2 y 3.");
            }
        } while (!sexoValido);
        nuevoCliente.setSexo(sexo);

        String ciudad;
        do {
            System.out.println("Ciudad: ");
            ciudad = input.nextLine().trim();
            if (ciudad.isBlank()) {
                System.out.println("Debe ingresar una ciudad. Vuelva a intentarlo.");
            }
        } while (ciudad.isBlank());
        nuevoCliente.setCiudad(ciudad);

        System.out.println("Fecha de nacimiento (dd-MM-yyyy): ");
        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        do {
            fechaStr = input.nextLine().trim();
            if (fechaStr.isBlank()) {
                mostrarErrorFecha();
                continue;
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                fechaNacimiento = LocalDate.parse(fechaStr, formatter);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                mostrarErrorFecha();
            }

        } while (!fechaValida);
        nuevoCliente.setFechaNacimiento(fechaNacimiento);

        String telefono;
        do {
            System.out.println("Teléfono: ");
            telefono = input.nextLine().trim();
            if (telefono.isBlank()) {
                System.out.println("⚠\uFE0F Debe ingresar un número de teléfono. Vuelva a intentarlo ⚠\uFE0F");
            } else if (!telefono.matches("\\d{7,15}")) {
                System.out.println("⚠\uFE0F Formato inválido. Debe contener entre 7 y 15 dígitos.");
                telefono = ""; // Forzar a volver a pedir
            }
        } while (telefono.isBlank());
        nuevoCliente.setTelefono(telefono);

        String email;
        do {
            System.out.println("Email: ");
            email = input.nextLine().trim();
            if (email.isBlank()) {
                System.out.println("Debe ingresar un email. Vuelva a intentarlo.");
            } else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}")) {
                System.out.println("Formato de email inválido. Debe ser ejemplo@dominio.com");
                email = ""; // Forzar a volver a pedir
            }
        } while (email.isBlank());
        nuevoCliente.setEmail(email);

        //Llamo al controller para guardar la información en la database.
        clienteController.agregarNuevoCliente(nuevoCliente);
    }

    private static void switchListar(ClienteController clienteController) {
        System.out.println("**** LISTAR TODOS LOS CLIENTES ****");
        List<Cliente> listaClientes = clienteController.listarClientes();
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados actualmente.");
        } else {
            for (Cliente c : listaClientes) {
                System.out.println(
                        "ID: " + c.getId() + " | Nombre: " + c.getNombre() + " " + c.getApellidos() + " | Sexo: " + c.getSexo() + " | Ciudad: " + c.getCiudad() + " | Fecha de nacimiento: "
                                + c.getFechaNacimiento() + " | Teléfono: " + c.getTelefono() + " | Correo electrónico: " + c.getEmail());
            }
            System.out.println("");
        }
    }

    private static void switchActualizar(Scanner input, ClienteController clienteController) {
        System.out.println("**** ACTUALIZAR INFORMACIÓN DE UN CLIENTE ****");

        Cliente actualizarCliente = null;
        long idCliente;
        do {
            System.out.println("Ingrese el ID del cliente que quiera actualizar.");
            try {
                idCliente = input.nextLong();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("⚠ Opción inválida. Debe ingresar un número de ID.");
                input.nextLine();
                continue;
            }

            actualizarCliente = clienteController.buscarPorId(idCliente);
            if (actualizarCliente == null) {
                System.out.println("Cliente no encontrado.");
            }
        } while (actualizarCliente == null);

        System.out.println("A tener en cuenta para actualizar un cliente:  ");
        System.out.println("El nombre y los apellidos, no pueden ser modificados.");
        System.out.println("Si desea mantener algún campo, simplemente presione la tecla ENTER. ");

        String sexo = "";
        boolean sexoValido = false;
        do {
            System.out.println("Sexo:");
            System.out.println("1 - Hombre");
            System.out.println("2 - Mujer");
            System.out.println("3 - No especificado");
            String opcionSexo = input.nextLine().trim();
            if (opcionSexo.isBlank()) {
                sexoValido = true;
                break;
            }
            switch (opcionSexo) {
                case "1":
                    sexo = "Hombre";
                    sexoValido = true;
                    break;
                case "2":
                    sexo = "Mujer";
                    sexoValido = true;
                    break;
                case "3":
                    sexo = "No especificado";
                    sexoValido = true;
                    break;
                default:
                    System.out.println("Opción inválida, seleccione una opción entre 1, 2 y 3.");
            }
        } while (!sexoValido);
        if (!sexo.isBlank()) {
            actualizarCliente.setSexo(sexo);
        }

        System.out.println("Ciudad:");
        String nuevaCiudad = input.nextLine().trim();
        if (!nuevaCiudad.isBlank()) {
            actualizarCliente.setCiudad(nuevaCiudad);
        }

        System.out.println("Fecha de nacimiento (dd-MM-yyyy): ");
        String nuevaFechaStr = input.nextLine().trim();
        if (!nuevaFechaStr.isBlank()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate nuevaFechaNacimiento = LocalDate.parse(nuevaFechaStr, formatter);
                actualizarCliente.setFechaNacimiento(nuevaFechaNacimiento);
            } catch (DateTimeParseException e) {
                mostrarErrorFecha();
            }
        }

        System.out.println("Número de teléfono: ");
        String nuevoTelefono = input.nextLine().trim();
        if (!nuevoTelefono.isBlank()) {
            if (nuevoTelefono.matches("\\d{7,15}")) {
                actualizarCliente.setTelefono(nuevoTelefono);
            } else {
                System.out.println("Número de teléfono inválido. El número de teléfono no ha cambiado.");
                System.out.println("El formato correcto es: '666666666'");
                System.out.println("Para volver a intentarlo, actualice de nuevo el cliente");
            }
        }

        System.out.println("Correo electrónico:");
        String nuevoEmail = input.nextLine().trim();
        if (!nuevoEmail.isBlank()) {
            if (nuevoEmail.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}")) {
                actualizarCliente.setEmail(nuevoEmail);
            } else {
                System.out.println("Correo electrónico inválido. El correo electrónico no ha cambiado.");
                System.out.println("El formato correcto es: ejemplo@ejemplo.com");
                System.out.println("Para volver a intentarlo, actualice de nuevo el cliente");
            }
        }

        clienteController.actualizarCliente(actualizarCliente);
        System.out.println("Cliente actualizado correctamente.");
    }

    private static void switchEliminar(Scanner input, ClienteController clienteController) {
        System.out.println("**** ELIMINAR UN CLIENTE ****");
        List<Cliente> clientesParaEliminar = clienteController.listarClientes();
        if (clientesParaEliminar.isEmpty()) {
            System.out.println("No hay clientes registrados actualmente.");
        }
        System.out.println("Ingrese el ID del cliente que quiera eliminar:");
        long idEliminar = input.nextLong();
        input.nextLine();
        clienteController.eliminarCliente(idEliminar);

    }

    private static void switchBuscarPorCiudad(Scanner input, ClienteController clienteController) {
        System.out.println("**** BUSCAR UN CLIENTE POR CIUDAD ****");
        System.out.println("Ingrese la ciudad para filtrar:");
        String ciudadABuscar = input.nextLine();

        List<Cliente> clientesPorCiudad = clienteController.buscarClientePorCiudad(ciudadABuscar);
        if (clientesPorCiudad.isEmpty()) {
            System.out.println(" ⚠\uFE0F No hay ninguna usuario registrado con la siguiente ciudad: " + ciudadABuscar);
        } else {
            System.out.println("✅ Clientes de la ciudad de " + ciudadABuscar + " :");
            for (Cliente c : clientesPorCiudad) {
                System.out.println(
                        "ID: " + c.getId() +
                                " | Nombre: " + c.getNombre() + " " + c.getApellidos() + " | Sexo: " + c.getSexo()
                                + " | Fecha de nacimiento: " + c.getFechaNacimiento() + " | Ciudad: " + c.getCiudad() +
                                " | Teléfono: " + c.getTelefono() + " | Email: " + c.getEmail()
                );
            }
        }
    }

}
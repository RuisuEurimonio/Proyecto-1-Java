/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.modelo.Producto;
import com.example.demo.modelo.RepositorioProducto;
import com.example.demo.vista.PantallaProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Clase encargada de mantener la relación entre el modelo y la vista del sistema
 * de inventario, llevando las distintas funcionalidades que se van a poder 
 * realizar en el sistema de inventario.
 * @author Luis Felipe Linares Perdomo.
 */
public class ControladorProducto implements ActionListener {

    /**
     * Inicializo las variables vista que sera una variable encapsulada de la
     * clase PantallaProducto.
     */
    private PantallaProducto vista;
    /**
     * Inicializo una variable modelo que sera una variable encapsulada de la 
     * clase RepositorioProducto.
     */
    private RepositorioProducto modelo;

    /**
     * Constructor encargado de asignar la vista y el modelo a unas variables,
     * además ejecuta la función de crearEventos para los botones, actualizar
     * la tabla con los datos actuales de la base de datos y tambien oculta el
     * botón de actualizar producto en la parte visual.
     * @param vista
     * @param modelo 
     */
    public ControladorProducto(PantallaProducto vista, RepositorioProducto modelo) {
        this.vista = vista;
        this.modelo = modelo;
        crearEventos();
        actualizarTabla();
        ocultarBoton();
    }

    /**
     * Función creada para ocultar el botón de actualizar producto en la parte
     * visual del programa, para no entrar en conflicto con el botón de agregar.
     */
    public void ocultarBoton() {
        vista.getBtnActProducto().setVisible(false);
    }

    /**
     * Función para asignarles los escucha de eventos a los distintos botones
     * creados anteriormente en la parte de vista.
     */
    public void crearEventos() {
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnBorrar().addActionListener(this);
        vista.getBtnInformes().addActionListener(this);
        vista.getBtnActProducto().addActionListener(this);
    }

    /**
     * Función encargada de estar al tanto de los eventos en la pantalla del
     * sistema de inventarios, donde comparará el lugar del evento con algún
     * botón y dependiendo de esto ejecutara una funcionalidad.
     * @param event 
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == vista.getBtnActualizar()) {
            actualizar();
        } else if (event.getSource() == vista.getBtnAgregar()) {
            agregar();
        } else if (event.getSource() == vista.getBtnBorrar()) {
            borrar();
        } else if (event.getSource() == vista.getBtnInformes()) {
            informes();
        } else if (event.getSource() == vista.getBtnActProducto()) {
            actualizarProducto();
        }
    }

    /**
     * Se inicializa una variable global que va a almacenar la fila seleccionada
     * en la tabla.
     */
    int fila;

    /**
     * Función que permitirá actualizar algán dato de la tabla que se ejecutará 
     * con la escucha del botón asignado.
     */
    public void actualizar() {
        //Actualiza el valor de la fila seleccionada.
        fila = vista.getTblDatos().getSelectedRow();
        //realizo la comparativa para saber si sí se selecciono una fila.
        if (fila > -1) {
            //Coloco visible al botón ocultado en la función ocultarFuncion
            vista.getBtnActProducto().setVisible(true);
            //Escondo el botón de agregar producto para no tener conflicto con el botón anterior.
            vista.getBtnAgregar().setVisible(false);
            //Actualizo el título del panel conservando todos los valores creados en la parte vista.
            vista.getPanelDatos().setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Actualizar un producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(0, 0, 0)));

            //Asigno a una variable el valor de cada columna de la base de datos con su respectivo valor.
            String nombre = vista.getTblDatos().getValueAt(fila, 1).toString();
            String precio = vista.getTblDatos().getValueAt(fila, 2).toString();
            String inventario = vista.getTblDatos().getValueAt(fila, 3).toString();

            //Asigno el valor de las variables anteriores a la parte visual.
            vista.getTxtNombre().setText(nombre);
            vista.getTxtPrecio().setText(precio);
            vista.getTxtInventario().setText(inventario);

            //Lanzo un mensaje avisando que se pueden actualizar el producto seleccionado.
            JOptionPane.showMessageDialog(vista, "Modifique los datos a actualizar en el siguiente panel de actualizar un producto.",
                    "Aviso Actualizar", JOptionPane.INFORMATION_MESSAGE);

        } else {
            //en caso de que no se halla seleccionado una columna se lanza un mensaje de error.
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un producto a actualizar.", "Error al actualizar un producto.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Función que sera la continuación de la función Actualizar, donde con el 
     * dato original en pantalla se podra borrar o modificar algún valor para
     * después ser guardado en la base de datos, en caso de NO necesitar modificar
     * nada solo se dejan los datos iguales y se presiona el botón de actualizar.
     */
    public void actualizarProducto() {
        //Validación de que las casillas si esten llenas por medio de una función que retorna un booleano.
        if (validarDatos()) {
            //Validación para asegurarse de que no se agregen números negativos a la base de datos por medio de un funcion que retorna un booleano.
            if (validarNumeros()) {
                //Se vuelve a ocultar el botón de actualizar producto.
                vista.getBtnAgregar().setVisible(true);
                //Se hace visible el botón para agregar producto.
                vista.getBtnActProducto().setVisible(false);

                //Guardo el código de la fila seleccionada en texto y la convierto a dato de tipo Long
                String idTexto = vista.getTblDatos().getValueAt(fila, 0).toString();
                Long codigo = Long.parseLong(idTexto);
                
                //Creo y asigno una valor a unas variables en base a lo que contenga la parte visual del programa.
                String nombre = vista.getTxtNombre().getText();
                Double precio = Double.parseDouble(vista.getTxtPrecio().getText());
                Integer inventario = Integer.parseInt(vista.getTxtInventario().getText());
                
                //Creo un nuevo producto actualizado con los datos de las variables anteriores y la Id original.
                Producto productoAct = new Producto(codigo, nombre, precio, inventario);
                //Se guarda en la base de datos este nuevo producto sobreescribiendo el anterior
                modelo.save(productoAct);
                //Se actualiza la tabla por medio de una función.
                actualizarTabla();
                
                //Se pone en blanco las casillas llenadas.
                vista.getTxtNombre().setText("");
                vista.getTxtPrecio().setText("");
                vista.getTxtInventario().setText("");
                
                //Se manda un mensaje de confirmación de que los datos fueron actualizados.
                vista.getPanelDatos().setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Agregar nuevo producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(0, 0, 0)));
                JOptionPane.showMessageDialog(vista, "Se ha actualizado el producto seleccionado.",
                        "Confirmación Actualización", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Función que sirve para agregar un nuevo producto a la base de datos en base
     * a la información agregada en la parte visual, en caso de estar vacia alguna 
     * casilla se lanza un error.
     */
    public void agregar() {
        //Se valida que los campos no esten vacíos por medio de una función que retorna un booleano.
        if (validarDatos()) {
            //Se valida que sean numeros positivos por medio de una función que retorna un booleano.
            if (validarNumeros()) {
                //Se crea un nuevo dato tipo producto con los datos de las casillas llenadas.
                Producto producto = new Producto(null,
                        vista.getTxtNombre().getText(),
                        Double.parseDouble(vista.getTxtPrecio().getText()),
                        Integer.parseInt(vista.getTxtInventario().getText()));
                
                //Se guarda el dato tipo producto en la base de datos.
                modelo.save(producto);
                
                //Se lanza un mensaje de confirmación que se agrego el producto.
                JOptionPane.showMessageDialog(vista, "Se ha registrado el producto",
                        "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                
                //Se pone en blanco las casillas de la parte visual.
                vista.getTxtNombre().setText("");
                vista.getTxtPrecio().setText("");
                vista.getTxtInventario().setText("");
                
                //Se actualiza la tabla con una función con los datos actuales de la base de datos.
                actualizarTabla();
            }
        }
    }

    /**
     * Función que permite borrar un dato de la base de datos por medio de su
     * Id seleccionada en la tabla.
     */
    public void borrar() {
        //Se guarda el número de la fila seleccionada en una variable.
        int fila = vista.getTblDatos().getSelectedRow();
        //Se valida si el número es mayor a -1, en caso de que no sea asi no se selecciono alguna fila.
        if (fila > -1) {
            //Se guarda la id de la fila seleccionada en texto y se convierte a Long.
            String idTexto = vista.getTblDatos().getValueAt(fila, 0).toString();
            Long codigo = Long.parseLong(idTexto);
            
            //Se elimina la fila seleccionada por medio de la id obtenida de la respectiva fila seleccionada.
            modelo.deleteById(codigo);
            
            //Se actualiza la tabla con uan función con los datos actuales de la base de datos.
            actualizarTabla();
            
            //Se lanza un mensaje de confirmación de que se elimino el producto.
            JOptionPane.showMessageDialog(vista, "Producto borrado de forma exitosa", "Confirmacion de borrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //En caso de no haber seleccionado alguna fila se lanza un error de aviso.
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un producto para borrar", "Error al borrar", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Función para generar el respectivo informe en base a los datos actuales 
     * de la base de datos, que lanzará un mensaje con el nombre del producto
     * con menor valor, mayor valor, el promedio de los precios y el valor total
     * del inventario actual.
     */
    public void informes() {

        //Creo una lista que va a tener los datos de la base de datos original.
        List<Producto> listaProductos = (List<Producto>) modelo.findAll();

        /**
         * variable que va a contener el nombre del producto menor, inicia con un valor vacío.
         */
        String menor = "";
        /**
         * variable que va a contener el número menor de los datos, y servira para la comparativa.
         */
        Double numeroMenor = 99999.0;
        /**
         * variable que va a contener el nombre del producto mayor, inicia con un valor vacío.
         */
        String mayor = "";
        /**
         * variable que va a contener el dato con el mayor precio, y servirá para la comparativa.
         */
        Double numeroMayor = 0.0;
        /**
         * variable que va a contener el precio total que servira para sacar el promedio
         */
        Double totalPrecio = 0.0;
        /**
         * variable que va a contener el precio total del inventario, inicia siendo cero.
         */
        Double total = 0.0;

        //Ciclo for que sirve para recorrer los valores de la lista para sacar el nombre menor.
        for (Producto productoActual : listaProductos) {
            //Creo una variable que va a tener el precio del producto actual en el ciclo.
            Double numeroActual = productoActual.getPrecio();
            //Se compara el valor de ese precio actual con el valor de la variable con el precio menor.
            if (numeroActual < numeroMenor) {
                //si es menor, este se guardara en la variable del precio menor para comparar en el siguiente ciclo.
                numeroMenor = productoActual.getPrecio();
                //Se guarda el nombre del item actual que tiene el precio menor.
                menor = productoActual.getNombre();
            }
        }
        
        //Ciclo for que sirve para recorrer los valores de la lista para sacar el nombre mayor.
        for (Producto productoActual : listaProductos) {
            //Creo una variable que va a tener el precio del producto actual en el ciclo.
            Double numeroActual = productoActual.getPrecio();
            //Se compara el valor de ese precio actual con el valor de la variable con el precio mayor.
            if (numeroActual > numeroMayor) {
                //si es mayor, este se guardara en la variable del precio mayor para comparar en el siguiente ciclo.
                numeroMayor = productoActual.getPrecio();
                //Se guarda el nombre del item actual que tiene el precio mayor.
                mayor = productoActual.getNombre();
            }
        }

        //Ciclo que sirve para recorrer los valores de la lista para sacar el precio total con un contador.
        for (Producto productoActual : listaProductos) {
            //Creo una variable que va a contener el precio del item actual.
            Double numeroActual = productoActual.getPrecio();
            //Se le suma el valor de la variable anterior al contador de totalPrecio
            totalPrecio = totalPrecio + numeroActual;
        }

        //Ciclo que sirve para recorrer los valores de la lista para sacar el total del inventario.
        for (Producto productoActual : listaProductos) {
            //Se guarda el resultado de la multiplicación del precio del item actual con el inventario del mismo.
            Double numeroActual = productoActual.getPrecio() * productoActual.getInventario();
            //Se guarda el resultado en un contador total.
            total = total + numeroActual;
        }

        //Se utiliza el valor del totalPrecio y se divide por la cantidad de datos de la lista.
        Double promedio = totalPrecio / listaProductos.size();

        //Lanzo un mensaje en pantalla con estos datos.
        JOptionPane.showMessageDialog(vista,
                "Producto con precio menor: " + menor
                + "\nProducto con precio mayor: " + mayor
                + "\nPromedio Precios: " + String.format("%.1f", promedio)
                + "\nValor de inventario: " + total,
                "Informe", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Función que actualizara la tabla con los datos actuales, donde primero se 
     * construye la estructura de la tabla y luego se asignan los distintos valores
     * a la tabla, además modifica el tamaño de la parte del código junto a su
     * título para que no sea visible.
     */
    public void actualizarTabla() {
        //Se crea el nuevo modelo de la tabla.
        DefaultTableModel modeloTabla = new DefaultTableModel();
        //Se le agregan las distintas columnas con su respectivo código.
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Inventario");

        //Le establecemos el modelo anterior a la tabla de la parte visual.
        vista.getTblDatos().setModel(modeloTabla);

        //Se agregan los valores a la tabla por medio de un forEach.
        modelo.findAll().forEach(producto -> {
            //Se crea un nuevo arreglo de 4 elementos.
            Object[] fila = new Object[4];
            //a cada fila se le agrega su respectiva parte.
            fila[0] = producto.getId();
            fila[1] = producto.getNombre();
            fila[2] = producto.getPrecio();
            fila[3] = producto.getInventario();
            //Se le agrega la actual fila a la tabla.
            modeloTabla.addRow(fila);
        });

        //Se asigna el tamaño 0 como maximo, mínimo y por defecto a la columna del código.
        vista.getTblDatos().getColumn(vista.getTblDatos().getColumnName(0)).setWidth(0);
        vista.getTblDatos().getColumn(vista.getTblDatos().getColumnName(0)).setMaxWidth(0);
        vista.getTblDatos().getColumn(vista.getTblDatos().getColumnName(0)).setMinWidth(0);
        //se asinga el tamaño 0 como maximo y mínimo al titulo del código.
        vista.getTblDatos().getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        vista.getTblDatos().getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
    }

    /**
     * Función para poder validar que los campos no esten vacíos, en caso de 
     * que este vacío alguno se ejecuta un mensaje de error y retornara un dato
     * booleano falso, y si ninguno esta vacío retornara un booleano true.
     * @return 
     */
    private boolean validarDatos() {
        //Compara si una cadena vacia es igual con cada campo de la parte visual, con solo uno basta para lanzar error,
        if ("".equals(vista.getTxtNombre().getText()) || "".equals(vista.getTxtPrecio().getText()) || "".equals(vista.getTxtInventario().getText())) {
            //Se lanza el mensaje de error.
            JOptionPane.showMessageDialog(vista, "Las casillas no pueden estar vacias", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Función para poder validad que los números sean positivos y no se 
     * ingrese ninguno negativo, en caso de que alguno sea negativo se lanza 
     * un mensaje de error y retorna falso, en caso de que no se cumpla 
     * retornara true.
     * @return 
     */
    private boolean validarNumeros() {
        //Compara si los datos ingresados son iguales o menores a 0, para validar si el numero es negativo.
        if (Double.parseDouble(vista.getTxtPrecio().getText()) <= 0 || Integer.parseInt(vista.getTxtInventario().getText()) <= 0) {
            //En caso de que se cumpla la condición se lanzara un mensaje de error y retornara falso.
            JOptionPane.showMessageDialog(vista, "Error al ingresas el precio y/o el inventario", "Error al agregar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}

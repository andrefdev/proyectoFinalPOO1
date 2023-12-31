package persistencia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import logica.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
public class BaseDeDatos {
    private List<Empleado> empleados = new ArrayList<>();
    private List<Gerente> gerentes = new ArrayList<>();
    private List<Producto> productos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Producto> productosTemp = new ArrayList<Producto>();
    private final String url ="jdbc:postgresql://db.byuiftitpxymesjwshot.supabase.co:5432/postgres?user=postgres&password=Loco23230211!";
    //private final Pattern patronDNI = Pattern.compile("^[0-9]{8}$");
    public List<Empleado> getEmpleados(){
        actualizarEmpleados();
        return empleados;
    }
    public boolean validarDni(String dni) {
        String regex = "^[0-9]{8}$";
        Pattern patronDNI = Pattern.compile(regex);
        Matcher matcher = patronDNI.matcher(dni);
        return matcher.matches();
    }
    public boolean validarRuc(String ruc) {
        String regex = "^[0-9]{8}$";
        Pattern patronDNI = Pattern.compile(regex);
        Matcher matcher = patronDNI.matcher(ruc);
        return matcher.matches();
    }
    public boolean validarFecha(String fecha) {
        String regex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        Pattern patronFecha = Pattern.compile(regex);
        Matcher matcher = patronFecha.matcher(fecha);
        return matcher.matches();
    }
    public boolean validarPrecio(String precio) {
        String regex = "^[0-9]{1,5}$";
        Pattern patronPrecio = Pattern.compile(regex);
        Matcher matcher = patronPrecio.matcher(precio);
        return matcher.matches();
    }
    public boolean validarCodigo(String precio) {
        String regex = "\\d{2,5}";
        Pattern patronPrecio = Pattern.compile(regex);
        Matcher matcher = patronPrecio.matcher(precio);
        return matcher.matches();
    }
    public void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // Manejo de excepciones
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // Manejo de excepciones
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Manejo de excepciones
                e.printStackTrace();
            }
        }
    }
    public void actualizarEmpleados() {
        empleados.clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from empleados");

            while (resultSet.next()) { // will traverse through all rows
                Integer dni = resultSet.getInt("dni");
                String nombre = resultSet.getString("nombre");
                Integer sueldo = resultSet.getInt("sueldo");
                Integer ruc = resultSet.getInt("ruc");
                Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");

                Empleado empleado = new Empleado(nombre, dni, fechaNacimiento, sueldo, ruc);
                empleados.add(empleado);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void actualizarGerentes() {
        gerentes.clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from gerentes");

            while (resultSet.next()) { // will traverse through all rows
                Integer dni = resultSet.getInt("dni");
                String nombre = resultSet.getString("nombre");
                Integer sueldo = resultSet.getInt("sueldo");
                Integer ruc = resultSet.getInt("ruc");
                Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");

                Gerente gerente = new Gerente(nombre, dni, fechaNacimiento,sueldo, ruc);
                gerentes.add(gerente);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void actualizarClientes() {
        clientes.clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from clientes");

            while (resultSet.next()) { // will traverse through all rows
                Integer dni = resultSet.getInt("dni");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");
                Cliente cliente = new Cliente(nombre,dni, fechaNacimiento, direccion);
                clientes.add(cliente);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void actualizarProductos() {
        productos.clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from productos");
            List<String> categoriasList = null;
            while (resultSet.next()) { // will traverse through all rows
                int codigo = resultSet.getInt("codigo");
                int precio = resultSet.getInt("precio");
                int stock = resultSet.getInt("stock");
                String marca = resultSet.getString("marca");
                Array categoriasArray = resultSet.getArray("categorias");
                String[] catArray = (String[]) categoriasArray.getArray();
                categoriasList = Arrays.asList(catArray);
                ArrayList<String> categoriasArrayList = new ArrayList<>(categoriasList);

                String modelo = resultSet.getString("modelo");

                Producto producto = new Producto(categoriasArrayList, marca, modelo, precio, codigo, stock);
                productos.add(producto);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    private void actualizarPedidos() {
        pedidos.clear();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from pedidos");
            while (resultSet.next()) { // will traverse through all rows
                Array productosArray = resultSet.getArray("productos");
                Object[] jsonValues = (Object[]) productosArray.getArray();

                int precio = resultSet.getInt("precio");

                String nombre = resultSet.getString("cliente");
                Cliente cli = buscarClienteNombre(nombre);
                Date fechaPedido = resultSet.getDate("fecha");


                String productosString = jsonValues.toString();
                Gson gson = new Gson();
                // Deserializar los productos y guardarlos en una lista
                ArrayList<Producto> productos = new ArrayList<>();
                JsonArray productosJsonArray = gson.fromJson(productosString, JsonArray.class);

                for (JsonElement elemento : productosJsonArray) {
                    Producto producto = gson.fromJson(elemento.getAsJsonArray(), Producto.class);
                    productos.add(producto);
                }

                Pedido pedido = new Pedido(productos,precio,cli,fechaPedido);
                pedidos.add(pedido);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }


    public Empleado buscarEmpleado(int dni) {
        actualizarEmpleados();
        for (Empleado empleado : empleados
        ) {
            if (empleado.getDni() == dni) {
                return empleado;
            }
        }
        return null;
    }
    public Gerente buscarGerente(int dni) {
        actualizarGerentes();
        for (Gerente gerente : gerentes
        ) {
            if (gerente.getDni() == dni) {
                return gerente;
            }
        }
        return null;
    }
    public Producto buscarProducto(int cod) {
        actualizarProductos();
        for (Producto producto : productos
        ) {
            if (producto.getCodigo() == cod) {
                return producto;
            }
        }
        return null;
    }
    public Cliente buscarCliente(int dni) {
        actualizarClientes();
        for (Cliente cliente : clientes)
        {
            if (cliente.getDni() == dni) {
                return cliente;
            }
        }
        return null;
    }
    public Pedido buscarPedido(int cod) {
        actualizarPedidos();
        for (Pedido pedido : pedidos)
        {
            if (pedido.getCodigo() == cod) {
                return pedido;
            }
        }
        return null;
    }
    public boolean existeEmpleado(int dni) {
        actualizarEmpleados();
        for (Empleado empleado : empleados
        ) {
            if (empleado.getDni() == dni) {
                return true;
            }
        }
        return false;
    }
    public boolean existeGerente(int dni) {
        actualizarGerentes();
        for (Gerente gerente : gerentes
        ) {
            if (gerente.getDni() == dni) {
                return true;
            }
        }
        return false;
    }
    public boolean existeProducto(int cod) {
        actualizarProductos();
        for (Producto producto : productos
        ) {
            if (producto.getCodigo() == cod) {
                return true;
            }
        }
        return false;
    }
    public boolean existeCliente(int dni) {
        actualizarClientes();
        for (Cliente cliente : clientes)
        {
            if (cliente.getDni() == dni) {
                return true;
            }
        }
        return false;
    }
    public boolean existePedido(int cod) {
        actualizarPedidos();
        for (Pedido pedido : pedidos)
        {
            if (pedido.getCodigo() == cod) {
                return true;
            }
        }
        return false;
    }
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        productosTemp.clear();
        actualizarProductos();
        if(categoria.equals("")){
            return productos;
        }
        for (Producto producto : productos)
        {
            ArrayList<String> cat = producto.getCategorias();
            for (String s : cat) {
                if (s.equals(categoria)) {
                    productosTemp.add(producto);
                }
            }
        }
        return productosTemp;
    }
    public void eliminarEmpleado(int dni) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            actualizarEmpleados();
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            Empleado emp = null;

            if (validarDni(String.valueOf(dni))) {
                if (existeEmpleado(dni)){
                    for (Empleado empleado : empleados)
                    {
                        if(empleado.getDni() == dni){
                            emp = empleado;
                        }
                    }
                    JOptionPane.showConfirmDialog(null, "Seguro de eliminar al empleado: " + emp.toString());
                }
                else {
                    JOptionPane.showMessageDialog(null, "No existe ningun empleado con ese dni");
                }
                statement = connection.prepareStatement("DELETE FROM empleados WHERE dni = ?");

                statement.setLong(1,dni);

                statement.executeUpdate();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void eliminarProducto(int cod) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);

            statement = connection.prepareStatement("DELETE FROM productos WHERE codigo = ?");

            statement.setLong(1, cod);

            try {
                statement.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "No existe el producto con el código: "+ cod);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void eliminarPedido(int cod) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);

            statement = connection.prepareStatement("DELETE FROM pedidos WHERE codigo = ?");

            statement.setLong(1, cod);

            try {
                statement.executeUpdate();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "No existe el pedidoi con el código: "+ cod);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void agregarEmpleado(Long dni, String nombre, int sueldo, int ruc, Date fechaNacimiento) {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            if(validarDni(String.valueOf(dni)) && validarRuc(String.valueOf(ruc))){
                statement = connection.prepareStatement("INSERT INTO empleados(dni, nombre, sueldo, ruc, fecha_nacimiento) VALUES (?,?,?,?,?)");
                statement.setLong(1,dni);
                statement.setString(2,nombre);
                statement.setInt(3,sueldo);
                statement.setInt(4,ruc);
                statement.setDate(5, fechaNacimiento);
                statement.executeUpdate();
                statement2 = connection.prepareStatement("INSERT INTO credenciales(usuario, contrasena, credencial_id_empleado) VALUES (?,?,?)");
                statement2.setString(1,nombre+"."+ruc);
                statement2.setString(2, Long.toString(dni));
                statement2.setLong(3,dni);
                statement2.executeUpdate();
                statement2.close();
                JOptionPane.showMessageDialog(null, "empleado agregado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null, "Dni incorrecto");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, "error "+ e.toString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
                closeResources(connection, statement, resultSet);
            }
        }
    public void agregarGerente(Long dni, String nombre, int sueldo, int ruc, Date fechaNacimiento) {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 =null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            if(validarDni(String.valueOf(dni)) && validarRuc(String.valueOf(ruc))) {
                statement = connection.prepareStatement("INSERT INTO gerentes(dni, nombre, sueldo, ruc, fecha_nacimiento) VALUES (?,?,?,?,?)");
                statement.setLong(1, dni);
                statement.setString(2, nombre);
                statement.setInt(3, sueldo);
                statement.setInt(4, ruc);
                statement.setDate(5, fechaNacimiento);
                statement.executeUpdate();
                statement2 = connection.prepareStatement("INSERT INTO credenciales(usuario, contrasena, credencial_id_gerente) VALUES (?,?,?)");
                statement2.setString(1, nombre + "." + ruc);
                statement2.setString(2, Long.toString(dni));
                statement2.setLong(3, dni);
                statement2.executeUpdate();
                statement2.close();
            }else{
                JOptionPane.showMessageDialog(null, "Dni o ruc incorrecto");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void agregarProducto(int codigo, int precio, String marca, ArrayList<String> categorias, String modelo ) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String[] catArray = categorias.toArray(new String[categorias.size()]);
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            if(validarCodigo(String.valueOf(codigo)) && validarPrecio(String.valueOf(precio))) {
                statement = connection.prepareStatement("INSERT INTO productos(codigo, precio, marca, categorias, modelo) VALUES (?,?,?,?,?)");
                statement.setInt(1, codigo);
                statement.setInt(2, precio);
                statement.setString(3, marca);
                Array categoriasArray = connection.createArrayOf("VARCHAR", catArray);
                statement.setArray(4, categoriasArray);
                statement.setString(5, modelo);
                statement.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "codigo o precio incorrecto");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void agregarCliente(int dni, String nombre, Date fechaNacimiento, String direccion) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            if(validarDni(String.valueOf(dni))) {
                statement = connection.prepareStatement("INSERT INTO clientes(dni, nombre, fecha_nacimiento, direccion ) VALUES (?,?,?,?)");
                statement.setInt(1, dni);
                statement.setString(2, nombre);
                statement.setDate(3, fechaNacimiento);
                statement.setString(4, direccion);
                statement.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "Dni incorrecto");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public void agregarPedido(int codigo, ArrayList<Producto> productos, int precio, Cliente cliente) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Gson gson = new Gson();

        ArrayList<String> productosJason = new ArrayList<>();
        for (Producto producto : productos
             ) {
            productosJason.add(gson.toJson(producto));
        }
        String[] arrayStringProductos = productosJason.toArray(new String[productosJason.size()]);

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement("INSERT INTO pedidos(productos , precio, cliente, fecha) VALUES (?,?,?,?)");
            Date fechaPedido = Date.valueOf(LocalDate.now());
            //statement.setInt(1, codigo);
            if(validarDni(String.valueOf(cliente.getDni()))){
                String nombreCliente = cliente.getNombre();
                Array sqlArray = connection.createArrayOf("jsonb", arrayStringProductos);
                statement.setArray(1, sqlArray);
                statement.setInt(2, precio);
                statement.setString(3, nombreCliente);
                statement.setDate(4, fechaPedido);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "pedidio agregado exitosamente");
            }else {
                JOptionPane.showMessageDialog(null, "el dni debe tener 8 caracteres");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }

    public List<Producto> obtenerProductos(){
        actualizarProductos();
        return productos;
    }
    public List<Empleado> obtenerEmpleados(){
        actualizarProductos();
        return empleados;
    }
    public DefaultTableModel mostrarEmpleados(){
        actualizarEmpleados();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Dni");
        model.addColumn("Nombre");
        model.addColumn("Sueldo");
        model.addColumn("Ruc");
        model.addColumn("Fecha de nacimiento");

        for (Empleado empleado : empleados
        ) {
            String dni = String.valueOf(empleado.getDni());
            String sueldo = String.valueOf(empleado.getSueldo());
            String ruc = String.valueOf(empleado.getRuc());
            // Añadir filas de datos
            model.addRow(new String[]{dni, empleado.getNombre(), sueldo, ruc, empleado.getFechaNacimiento().toString()});
        }
        return model;
    }
    public DefaultTableModel mostrarPedidos(){
        actualizarPedidos();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Productos");
        model.addColumn("Precio");
        model.addColumn("Cliente");
        model.addColumn("Fecha de pedido");

        for (Pedido pedido : pedidos) {
            ArrayList productos = pedido.getProductos();
            int precio = pedido.getPrecio();
            Cliente c = pedido.getCliente();
            Date date = pedido.getFecha();
            model.addRow(new String[]{productos.toString(), String.valueOf(precio), c.toString(), date.toString()});
        }
        return model;
    }
    public void obtenerCredenciales(int dniEmpleado) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT A.dni, A.nombre, A.sueldo, A.ruc, A.fecha_nacimiento, B.credencial_id, B.usuario, B.contrasena FROM empleados AS A INNER JOIN credenciales as B ON A.dni=B.credencial_id_empleado OR A.dni=B.credencial_id_gerente";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int dni = resultSet.getInt("dni");
                String nombre = resultSet.getString("nombre");
                String usuarioCredencial = resultSet.getString("usuario");
                String contrasena = resultSet.getString("contrasena");

                System.out.println("DNI: " + dni);
                System.out.println("Nombre: " + nombre);
                System.out.println("Usuario: " + usuarioCredencial);
                System.out.println("Contraseña: " + contrasena);
            } else {
                System.out.println("No se encontraron credenciales para el empleado con DNI: " + dniEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    public Empleado obtenerEmpleadoConCredenciales(String usuario, String contrasena) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Empleado emp = null;
        try {
            String query = "SELECT A.dni, A.nombre, A.sueldo, A.ruc, A.fecha_nacimiento, B.credencial_id_gerente, B.usuario, B.contrasena FROM empleados AS A INNER JOIN credenciales as B ON A.dni=B.credencial_id_empleado WHERE B.usuario = ? AND B.contrasena = ?";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);

            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int dni = resultSet.getInt("dni");
                String nombre = resultSet.getString("nombre");
                String usuarioCredencial = resultSet.getString("usuario");
                String contrasenaTemp = resultSet.getString("contrasena");

                emp = buscarEmpleado(dni);
            } else {
                System.out.println("No se encontraron credenciales para el empleado con DNI: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return emp;
    }
    public Boolean existeEmpleadoConCredenciales(String usuario, String contrasena) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT A.dni, A.nombre, A.sueldo, A.ruc, A.fecha_nacimiento, B.credencial_id_gerente, B.usuario, B.contrasena FROM empleados AS A INNER JOIN credenciales as B ON A.dni=B.credencial_id_empleado WHERE B.usuario = ? AND B.contrasena = ?";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);

            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return false;
    }
    public Gerente obtenerGerenteConCredenciales(String usuario, String contrasena) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Gerente ger = null;
        try {
            String query = "SELECT A.dni, A.nombre, A.sueldo, A.ruc, A.fecha_nacimiento, B.credencial_id_gerente, B.usuario, B.contrasena FROM gerentes AS A INNER JOIN credenciales as B ON A.dni=B.credencial_id_gerente WHERE B.usuario = ? AND B.contrasena = ?";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);

            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int dni = resultSet.getInt("dni");
                String nombre = resultSet.getString("nombre");
                String usuarioCredencial = resultSet.getString("usuario");
                String contrasenaTemp = resultSet.getString("contrasena");

                ger = buscarGerente(dni);
            } else {
                System.out.println("No se encontraron credenciales para el gerente con DNI: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return ger;
    }
    public Boolean existeGerenteConCredenciales(String usuario, String contrasena) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT A.dni, A.nombre, A.sueldo, A.ruc, A.fecha_nacimiento, B.credencial_id_gerente, B.usuario, B.contrasena FROM gerentes AS A INNER JOIN credenciales as B ON A.dni=B.credencial_id_gerente WHERE B.usuario = ? AND B.contrasena = ?";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);

            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
        return false;
    }
}
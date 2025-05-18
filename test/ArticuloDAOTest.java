import datarsians.DAO.factory.DAOFactory;
import datarsians.DAO.factory.TipoDAO;
import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.modelo.Articulo;
import java.util.List;

public class ArticuloDAOTest {
    public static void main (String[] arg) throws Exception {

        DAOFactory factory = DAOFactory.getDAOFactory(TipoDAO.HIBERNATE);
        ArticuloDAO articuloDAO = factory.getArticuloDAO();

        List<Articulo> articulos = articuloDAO.obtenerTodos();

        if (articulos != null && !articulos.isEmpty()) {
            System.out.println("articulos obtenidos: ");
            for (Articulo articulo : articulos) {
                System.out.println(articulo);
            }
        } else {
            System.out.println("No se han encontrado artículos en la BBDD.");
        }
    }
}
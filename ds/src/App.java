import controllers.ItemController;

public class App {
    public static void main(String[] args) throws Exception {
        ItemController controller = new ItemController(); //interacao entre model e view
        controller.iniciar();
    }
}

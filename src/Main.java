import controller.TotemController;
import server.SimpleHttpServer;

public class Main {
    public static void main(String[] args) {
        try {
            TotemController controller = new TotemController();
            SimpleHttpServer server = new SimpleHttpServer(controller);
            
            int porta = 8080;
            server.start(porta);
            
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║  TOTEM DE AUTOATENDIMENTO - CAFETERIA  ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();
            System.out.println("Acesse: http://localhost:" + porta);
            System.out.println();
            System.out.println("Pressione Ctrl+C para encerrar o servidor");
            
        } catch (Exception e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
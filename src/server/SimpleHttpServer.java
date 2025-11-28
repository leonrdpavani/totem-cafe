package server;

import controller.TotemController;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleHttpServer {
    private TotemController controller;
    private HttpServer server;

    public SimpleHttpServer(TotemController controller) {
        this.controller = controller;
    }

    public void start(int porta) throws IOException {
        server = HttpServer.create(new InetSocketAddress(porta), 0);
        
        server.createContext("/api/produtos", new ProdutosHandler());
        server.createContext("/api/carrinho", new CarrinhoHandler());
        server.createContext("/api/adicionar", new AdicionarHandler());
        server.createContext("/api/remover", new RemoverHandler());
        server.createContext("/api/finalizar", new FinalizarHandler());
        server.createContext("/api/resetar", new ResetarHandler());
        server.createContext("/", new StaticFileHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("Servidor rodando em http://localhost:" + porta);
    }

    class ProdutosHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String response = controller.getProdutosJson();
            sendResponse(exchange, response, 200);
        }
    }

    class CarrinhoHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String response = controller.getCarrinhoJson();
            sendResponse(exchange, response, 200);
        }
    }

    class AdicionarHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine();
                
                String[] params = query.split("&");
                int produtoId = Integer.parseInt(params[0].split("=")[1]);
                int quantidade = Integer.parseInt(params[1].split("=")[1]);
                
                String response = controller.adicionarAoCarrinho(produtoId, quantidade);
                sendResponse(exchange, response, 200);
            }
        }
    }

    class RemoverHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine();
                
                int produtoId = Integer.parseInt(query.split("=")[1]);
                
                String response = controller.removerDoCarrinho(produtoId);
                sendResponse(exchange, response, 200);
            }
        }
    }

    class FinalizarHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine();
                
                String formaPagamento = query.split("=")[1];
                
                String response = controller.finalizarPedido(formaPagamento);
                sendResponse(exchange, response, 200);
            }
        }
    }

    class ResetarHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            controller.resetarPedido();
            sendResponse(exchange, "{\"status\": \"resetado\"}", 200);
        }
    }

    class StaticFileHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            String filePath = "frontend" + path;
            File file = new File(filePath);
            
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                
                String contentType = getContentType(filePath);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, bytes.length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } else {
                String response = "404 - Arquivo não encontrado";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".json")) return "application/json";
        return "text/plain";
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
package com.anujamart;

import com.anujamart.model.Product;
import com.anujamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/api/chat")
public class ChatbotServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() {
        this.productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String userMessage = null;

        // Support both application/json and form-urlencoded
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            String rawJson = sb.toString();
            userMessage = extractFieldFromJson(rawJson, "message");
        } else {
            userMessage = request.getParameter("message");
        }

        if (userMessage == null || userMessage.trim().isEmpty()) {
            writeJsonResponse(response, "Hello! I am your ANUJA MART AI Assistant. How can I help you today? You can ask me about products, orders, categories, or how to sell on our platform!");
            return;
        }

        userMessage = userMessage.trim();

        // 1. Try external AI API (Gemini) if API key is configured
        String apiKey = getAiApiKey();
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            try {
                String aiReply = callGeminiApi(userMessage, apiKey);
                if (aiReply != null && !aiReply.trim().isEmpty()) {
                    writeJsonResponse(response, aiReply);
                    return;
                }
            } catch (Exception e) {
                System.err.println("Gemini API call failed, falling back to local store assistant: " + e.getMessage());
            }
        }

        // 2. Intelligent, Store-Aware Local Assistant Fallback
        String fallbackReply = generateStoreAwareReply(userMessage);
        writeJsonResponse(response, fallbackReply);
    }

    private String getAiApiKey() {
        String key = System.getenv("GEMINI_API_KEY");
        if (key == null || key.trim().isEmpty()) {
            key = System.getenv("AI_API_KEY");
        }
        if (key == null || key.trim().isEmpty()) {
            key = System.getProperty("gemini.api.key");
        }
        return key;
    }

    private String callGeminiApi(String prompt, String apiKey) throws Exception {
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(12000);
        conn.setDoOutput(true);

        String storeContext = "You are Anuja, the helpful and friendly AI Shopping Assistant for ANUJA MART (an online multi-seller e-commerce marketplace). Keep responses polite, concise (max 3 sentences), and directly helpful regarding shopping, categories (Electronics, Fashion, Beauty, Grocery, Home), orders, or selling.";
        String escapedPrompt = escapeJson(storeContext + "\nUser question: " + prompt);
        String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + escapedPrompt + "\"}]}]}";

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        if (code != 200) {
            throw new IOException("Gemini API returned HTTP status: " + code);
        }

        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                responseBody.append(line);
            }
        }

        String json = responseBody.toString();
        // Parse candidate text from JSON
        int textIdx = json.indexOf("\"text\": \"");
        if (textIdx != -1) {
            int start = textIdx + 9;
            int end = json.indexOf("\"", start);
            if (end != -1) {
                String text = json.substring(start, end);
                return unescapeJson(text);
            }
        }
        return null;
    }

    private String generateStoreAwareReply(String msg) {
        String lower = msg.toLowerCase();

        if (lower.contains("hello") || lower.contains("hi") || lower.contains("hey")) {
            return "Hello and welcome to ANUJA MART! 🛍️ I am your AI assistant. You can ask me about trending products, track your orders, filter by category, or learn how to sell!";
        }

        if (lower.contains("order") || lower.contains("track") || lower.contains("status")) {
            return "📦 You can view and track all your orders by logging in and navigating to the 'My Orders' section from the top navigation bar. There you'll find order status (CONFIRMED, SHIPPED, DELIVERED) and item breakdown.";
        }

        if (lower.contains("seller") || lower.contains("sell") || lower.contains("vendor")) {
            return "💼 Want to sell on ANUJA MART? Simply register an account with the 'Seller' role! You'll get access to the Seller Central dashboard to add products, manage inventory, and fulfill incoming customer orders.";
        }

        if (lower.contains("pay") || lower.contains("payment") || lower.contains("upi") || lower.contains("card")) {
            return "💳 We support convenient and secure payment methods including Mock UPI, Debit/Credit Card, and Net Banking with instant order confirmation!";
        }

        if (lower.contains("delivery") || lower.contains("ship") || lower.contains("shipping")) {
            return "🚚 Orders on ANUJA MART are dispatched quickly by our verified sellers and delivered within 2-4 business days directly to your shipping address.";
        }

        if (lower.contains("return") || lower.contains("refund")) {
            return "🛡️ ANUJA MART offers a 7-day hassle-free return and replacement policy for eligible damaged or defective items.";
        }

        // Category queries
        if (lower.contains("category") || lower.contains("categories")) {
            return "🏷️ We currently offer top quality products across 5 main categories: Electronics, Fashion, Beauty, Grocery, and Home. You can click any category pill on the products page to filter!";
        }

        // Check if query is about finding products
        try {
            List<Product> matches = productService.searchAndFilter(msg, null);
            if (!matches.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("🔍 I found ").append(matches.size()).append(" matching item(s) in our store:\n");
                int limit = Math.min(3, matches.size());
                for (int i = 0; i < limit; i++) {
                    Product p = matches.get(i);
                    sb.append("• ").append(p.getName()).append(" (₹").append(String.format("%.2f", p.getPrice())).append(")\n");
                }
                sb.append("Browse them right now on our Products page!");
                return sb.toString();
            }
        } catch (Exception ignored) {
        }

        return "💡 I can assist you with product recommendations, order tracking, category browsing, and seller registration. Feel free to search for any item above or browse our catalog!";
    }

    private void writeJsonResponse(HttpServletResponse response, String reply) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("{\"reply\":\"" + escapeJson(reply) + "\",\"status\":\"ok\"}");
        out.flush();
    }

    private String extractFieldFromJson(String json, String field) {
        String key = "\"" + field + "\":";
        int idx = json.indexOf(key);
        if (idx == -1) return null;
        int start = json.indexOf("\"", idx + key.length());
        if (start == -1) return null;
        int end = json.indexOf("\"", start + 1);
        if (end == -1) return null;
        return json.substring(start + 1, end);
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String unescapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }
}

package com.pahanaedu.bookshop.persistence.servlet;

import com.pahanaedu.bookshop.persistence.reports.service.ReportService;
import com.pahanaedu.bookshop.persistence.reports.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet for handling report generation requests
 * Uses the same database connection pattern as existing code
 */
@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
    private final ReportService reportService;
    private final Gson gson;

    public ReportServlet() {
        this.reportService = new ReportService();
        this.gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is authenticated and is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            sendErrorResponse(response, "Unauthorized access", 401);
            return;
        }

        String action = request.getParameter("action");
        String reportType = request.getParameter("type");

        try {
            switch (action) {
                case "generate":
                    generateReport(response, reportType);
                    break;
                case "types":
                    getAvailableReportTypes(response);
                    break;
                default:
                    sendErrorResponse(response, "Invalid action: " + action, 400);
            }
        } catch (Exception e) {
            sendErrorResponse(response, "Error processing request: " + e.getMessage(), 500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is authenticated and is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            sendErrorResponse(response, "Unauthorized access", 401);
            return;
        }

        String action = request.getParameter("action");
        String reportType = request.getParameter("type");

        try {
            switch (action) {
                case "generate":
                    generateReport(response, reportType);
                    break;
                default:
                    sendErrorResponse(response, "Invalid action: " + action, 400);
            }
        } catch (Exception e) {
            sendErrorResponse(response, "Error processing request: " + e.getMessage(), 500);
        }
    }

    /**
     * Generate a specific type of report
     */
    private void generateReport(HttpServletResponse response, String reportType) throws IOException {
        if (reportType == null || reportType.trim().isEmpty()) {
            sendErrorResponse(response, "Report type is required", 400);
            return;
        }

        if (!reportService.isValidReportType(reportType)) {
            sendErrorResponse(response, "Invalid report type: " + reportType, 400);
            return;
        }

        try {
            ReportDTO report = reportService.generateReport(reportType);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Report generated successfully");
            result.put("data", report);
            
            sendJsonResponse(response, result);
        } catch (Exception e) {
            sendErrorResponse(response, "Failed to generate report: " + e.getMessage(), 500);
        }
    }

    /**
     * Get available report types
     */
    private void getAvailableReportTypes(HttpServletResponse response) throws IOException {
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", reportService.getAvailableReportTypes());
            
            sendJsonResponse(response, result);
        } catch (Exception e) {
            sendErrorResponse(response, "Failed to get report types: " + e.getMessage(), 500);
        }
    }

    /**
     * Send JSON response
     */
    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(data));
            out.flush();
        }
    }

    /**
     * Send error response
     */
    private void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        error.put("statusCode", statusCode);
        
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(error));
            out.flush();
        }
    }
} 
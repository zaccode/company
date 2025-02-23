<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bms.Loan" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Request Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .card {
            max-width: 500px;
            margin: auto;
            border-radius: 12px;
        }

        .card-header {
            border-radius: 12px 12px 0 0;
            font-weight: bold;
        }

        .form-control {
            border-radius: 8px;
        }

        .btn {
            width: 45%;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="card shadow-lg">
            <div class="card-header text-center bg-primary text-white">
                <h3>Loan Request Form</h3>
            </div>
            <div class="card-body">
                <%
                    // Retrieve Loan object from session
                    HttpSession sess = request.getSession();
                    Loan lr = (Loan) sess.getAttribute("loan");

                    // If the loan object is null, create a new instance
                    if (lr == null) {
                        lr = new Loan("reload this site", "Ujval", "1234", "");
                        sess.setAttribute("loan", lr);
                    }
                %>
                <form action="LoanServlet" method="post">
                    <div class="mb-3">
                        <label for="ssnId" class="form-label">SSN ID</label>
                        <input type="text" class="form-control" id="ssnId" name="ssnId" value="<%= lr.getSSNID() %>" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="customerName" class="form-label">Customer Name</label>
                        <input type="text" class="form-control" id="customerName" name="name" value="<%= lr.getCustomer_Name() %>" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="accountNumber" class="form-label">Account Number</label>
                        <input type="text" class="form-control" id="accountNumber" name="accountNumber" value="<%= lr.getAccount_Number() %>" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="aadharNumber" class="form-label">Aadhar Number</label>
                        <input type="text" class="form-control" id="aadharNumber" name="aadharNumber" value="<%= lr.getAadhar_Number() %>" placeholder="Enter 12-digit Aadhar Number" required pattern="\d{12}">
                    </div>
                    <div class="mb-3">
                        <label for="amount" class="form-label">Loan Amount</label>
                        <input type="number" class="form-control" id="amount" name="amount" value="<%= lr.getAmount() != null ? lr.getAmount() : "" %>" placeholder="Enter Loan Amount" required min="1" step="0.01">
                    </div>
                    <div class="mb-3">
                        <label for="loanReason" class="form-label">Reason for Loan</label>
                        <textarea class="form-control" id="loanReason" name="loanReason" placeholder="Explain why you need the loan..." rows="4" required><%= lr.getMessage() != null ? lr.getMessage() : "" %></textarea>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-success">Submit Loan Request</button>
                        <button type="reset" class="btn btn-danger">Reset</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

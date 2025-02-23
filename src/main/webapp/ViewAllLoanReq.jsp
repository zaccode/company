<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bms.Loan" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .table-container {
            margin-top: 30px;
        }
        .status-pending {
            color: orange;
            font-weight: bold;
        }
        .status-approved {
            color: green;
            font-weight: bold;
        }
        .status-rejected {
            color: red;
            font-weight: bold;
        }
        .message-icon {
            cursor: pointer;
            color: blue;
        }
    </style>
</head>
<body>
    <div class="container table-container">
        <h2 class="text-center">Loan Requests</h2>
        <%                  
        List<Loan> accountList = (List<Loan>) request.getAttribute("accountList");
        if (accountList != null && !accountList.isEmpty()) {
        %>

        <table class="table table-bordered text-center">
            <thead class="table-dark">
                <tr>
                    <th>Request ID</th>
                    <th>SSN ID</th>
                    <th>Customer Name</th>
                    <th>Account Number</th>
                    <th>Loan Amount</th>
                    <th>Status</th>
                    <th>Message</th>
                </tr>
            </thead>
            <tbody>
                <%
                for (Loan acc : accountList) {
                %>
                <tr>
                    <td><%= acc.getReqId() %></td>
                    <td><%= acc.getSSNID() %></td>
                    <td><%= acc.getCustomer_Name() %></td>
                    <td><%= acc.getAccount_Number() %></td>
                    <td><%= acc.getAmount() %></td>
                    <td class="<%= acc.getStatus().equalsIgnoreCase("approved") ? "status-approved" : (acc.getStatus().equalsIgnoreCase("pending") ? "status-pending" : "status-rejected") %>">
                        <%= acc.getStatus() %>
                    </td>
                    <td>
                        <span class="message-icon" data-bs-toggle="modal" data-bs-target="#messageModal"
                              data-message="<%= acc.getMessage() %>"
                              data-bank-message="<%= acc.getBankMessage() %>"
                              data-created-at="<%= acc.getCreated_At() %>">📩</span>
                    </td>
                </tr>
                <%
                }
                %>
            </tbody>
        </table>
        <%
        } else {
        %>
        <table class="table table-bordered text-center">
            <tr>
                <td colspan="7" class="text-center">No accounts found.</td>
            </tr>
        </table>
        <%
        }
        %>
    </div>

    <!-- Message Modal -->
    <div class="modal fade" id="messageModal" tabindex="-1" aria-labelledby="messageModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="messageModalLabel">Loan Status Message</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p><strong>Message:</strong> <span id="modalMessage"></span></p>
                    <p><strong>Bank Message:</strong> <span id="modalBankMessage"></span></p>
                    <p><strong>Created At:</strong> <span id="modalCreatedAt"></span></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Update modal content dynamically when clicking on the message icon
        document.querySelectorAll(".message-icon").forEach(icon => {
            icon.addEventListener("click", function() {
                document.getElementById("modalMessage").innerText = this.getAttribute("data-message");
                document.getElementById("modalBankMessage").innerText = this.getAttribute("data-bank-message");
                document.getElementById("modalCreatedAt").innerText = this.getAttribute("data-created-at");
            });
        });
    </script>
</body>
</html>

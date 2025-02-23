<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        .search-box {
            width: 250px;
            margin-bottom: 10px;
            float: right;
        }
    </style>
</head>
<body>
    <div class="container table-container">
        <h2 class="text-center">Loan Requests</h2>

        <!-- Search Box -->
        <!-- <input type="text" id="searchInput" class="form-control search-box" placeholder="Search Loan Requests..."> -->
<!-- Search Box -->
<div class="input-group search-box">
    <input type="text" id="searchInput" class="form-control" placeholder="Search Loan Requests...">
    <button class="btn btn-dark" id="searchButton">
        🔍
    </button>
</div>
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
                    <th>Action</th>
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
                    <td><button class="btn btn-success open-status-modal" data-id="<%= acc.getReqId() %>">Approve</button></td>
                </tr>
                <%
                }
                %>
            </tbody>
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

    <!-- Status Change Modal -->
    <div class="modal fade" id="statusModal" tabindex="-1" aria-labelledby="statusModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Update Loan Status</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="statusForm" method ="post" action="ViewAllLoanByEmployee">
                        <input type="hidden" id="loanRequestId" name="reqId">
                        <div class="mb-3">
                            <label for="statusSelect" class="form-label">Change Status</label>
                            <select class="form-select" id="statusSelect">
                                <option value="Pending Approval" name="status">Pending Approval</option>
                                <option value="Approved" name="status">Approved</option>
                                <option value="Rejected" name="status">Rejected</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="statusReason" class="form-label">Reason</label>
                            <textarea class="form-control" name="reason" id="statusReason" rows="3" placeholder="Enter reason for status change"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </form>
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
        // Message Modal
       // document.querySelectorAll(".message-icon").forEach(icon => {
         //   icon.addEventListener("click", function() {
           //     document.getElementById("modalMessage").innerText = this.getAttribute("data-message");
            //});
       // });

        // Open Status Modal
        document.querySelectorAll(".open-status-modal").forEach(button => {
            button.addEventListener("click", function() {
                let requestId = this.getAttribute("data-id");
                document.getElementById("loanRequestId").value = requestId;
                new bootstrap.Modal(document.getElementById("statusModal")).show();
            });
        });

        // Handle Status Update
        document.getElementById("statusForm").addEventListener("submit", function(event) {
            event.preventDefault();
            let requestId = document.getElementById("loanRequestId").value;
            let newStatus = document.getElementById("statusSelect").value;
            let reason = document.getElementById("statusReason").value;

            let row = document.querySelector(`button[data-id="${requestId}"]`).closest("tr");
            let statusCell = row.querySelector("td:nth-child(6)");
            let statusClass = newStatus === "Approved" ? "status-approved" : newStatus === "Rejected" ? "status-rejected" : "status-pending";

            statusCell.className = statusClass;
            statusCell.innerText = newStatus;
            
            alert(`Loan request ${requestId} updated to ${newStatus}.\nReason: ${reason}`);
            bootstrap.Modal.getInstance(document.getElementById("statusModal")).hide();
        });

        // Search Filter
        document.getElementById("searchInput").addEventListener("keyup", function() {
            let filter = this.value.toLowerCase();
            document.querySelectorAll("#loanTable tr").forEach(row => {
                let text = row.innerText.toLowerCase();
                row.style.display = text.includes(filter) ? "" : "none";
            });
        });
        // Search Filter Function
function searchLoans() {
    let filter = document.getElementById("searchInput").value.toLowerCase();
    document.querySelectorAll("#loanTable tr").forEach(row => {
        let text = row.innerText.toLowerCase();
        row.style.display = text.includes(filter) ? "" : "none";
    });
}

// Event Listeners for Search
document.getElementById("searchButton").addEventListener("click", searchLoans);
document.getElementById("searchInput").addEventListener("keyup", function(event) {
    if (event.key === "Enter") searchLoans();
});

    </script>
</body>
</html>

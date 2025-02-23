home.jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="home.css">
    <title>Bank Management System</title>
    
</head>

<body>
   <div>
    <nav class="navbar navbar-expand-lg fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="loggedHome.jsp">
                <img src="./images/bank-icon.png" alt="Bank Icon" style="width: 40px; height: 40px; margin-right: 10px;">
                Bank Management System
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="./Registration/jsp/CustomerReg.jsp">Customer SignUp</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./Login/jsp/customerLogin.jsp">Customer SignIn</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./Registration/jsp/EmployeeReg.jsp">Employee SignUp</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./Login/jsp/employeeLogin.jsp">Employee SignIn</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>

<div style="padding-top: 60px;">
    <div align="center">
        <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                    aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                    aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="./images/carousel-1.png"
                        class="carousel-img d-block w-100" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Bank of ILP</h5>
                        <p>Where hopes meet reality...</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="./images/carousel-2.jpg" class="carousel-img d-block w-100" alt="...">
                  
                </div>
                <div class="carousel-item">
                    <img src="./images/carousel-3.jpg" class="carousel-img d-block w-100" alt="...">
                  
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden=
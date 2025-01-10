<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="util.SessionUtils" %>
<% boolean connected = SessionUtils.isUserConnected(request); %>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Ingrédient</title>
    <link rel="stylesheet" href="path/to/your/css/styles.css">
</head>
<body>
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
        <div class="layout-container">
            <%@include file="vertical-menu.jsp"%>
            <!-- Layout container -->
            <div class="layout-page">
                <!-- Navbar -->
                <nav class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme" id="layout-navbar">
                    <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
                        <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                            <i class="bx bx-menu bx-sm"></i>
                        </a>
                    </div>
                    <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
                        <ul class="navbar-nav flex-row align-items-center ms-auto">
                            <!-- User -->
                            <%@ include file="user.jsp" %>
                            <!--/ User -->
                        </ul>
                    </div>
                </nav>
                <!-- / Navbar -->
                <!-- Content wrapper -->
                <div class="content-wrapper">
                    <!-- Content -->
                    <div class="container-xxl flex-grow-1 container-p-y">
                        <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Formulaire /</span> Ajouter un Ingrédient</h4>
                        <div class="row">
                            <div class="col-lg-6 mx-auto">
                                <div class="card mb-4">
                                    <div class="card-header d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0">Ingrédient</h5>
                                    </div>
                                    <div class="card-body">
                                        <form method="POST" action="add-ingredient">
                                            <div class="mb-3">
                                                <label for="idIngredient" class="form-label">ID Ingrédient</label>
                                                <input type="number" class="form-control" id="idIngredient" name="idIngredient" required>
                                            </div>
                                            <div class="mb-3">
                                                <label for="idType" class="form-label">ID Type</label>
                                                <input type="number" class="form-control" id="idType" name="idType" required>
                                            </div>
                                            <div class="mb-3">
                                                <label for="qtt" class="form-label">Quantité</label>
                                                <input type="number" class="form-control" id="qtt" name="qtt" required>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Ajouter</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- / Content -->
                </div>
                <!-- / Content wrapper -->
            </div>
            <!-- / Layout container -->
        </div>
    </div>
    <!-- / Layout wrapper -->
    <%@include file="footer.jsp"%>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.Sale, java.util.ArrayList, util.SessionUtils" %>
<% boolean connected = SessionUtils.isUserConnected(request); %>
<%@include file="header.jsp"%>
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
                    <!-- Search modal button trigger -->
                    <button type="button" class="btn btn-icon rounded-pill btn-secondary mx-auto me-2" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <span class="tf-icons bx bx-search"></span>
                    </button>
                    <!-- /Search modal button trigger -->
                    <ul class="navbar-nav flex-row align-items-center">
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
                    <!-- Search modal -->
                    <div class="modal fade" id="searchModal" tabindex="-1" style="display: none;" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Critères de recherche</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form method="GET" action="sales">
                                        <div class="mb-3">
                                            <label class="form-label" for="search-id-user">ID Utilisateur</label>
                                            <input name="searchIdUser" type="text" class="form-control" id="search-id-user" placeholder="ID Utilisateur" aria-label="ID Utilisateur" aria-describedby="search-id-user">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label" for="search-min-date">Date de vente minimum</label>
                                            <input name="searchMinDate" type="date" class="form-control" id="search-min-date" placeholder="Date de vente minimum" aria-label="Date de vente minimum" aria-describedby="search-min-date">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label" for="search-max-date">Date de vente maximum</label>
                                            <input name="searchMaxDate" type="date" class="form-control" id="search-max-date" placeholder="Date de vente maximum" aria-label="Date de vente maximum" aria-describedby="search-max-date">
                                        </div>
                                        <div class="modal-footer p-0">
                                            <button type="reset" class="btn btn-outline-secondary" data-bs-dismiss="modal">Annuler</button>
                                            <button type="submit" class="btn btn-primary">Rechercher</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Search modal -->
                    <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Gotta taste /</span> Ventes</h4>
                    <!-- Basic Bootstrap Table -->
                    <div class="card">
                        <h5 class="card-header">Liste des ventes</h5>
                        <% if (connected) { %>
                        <div class="card-body"><a href="form-sale" type="button" class="btn btn-success">Ajouter</a></div>
                        <% } %>
                        <div class="table-responsive text-nowrap" style="overflow: auto visible">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>ID Utilisateur</th>
                                        <th>Date de vente</th>
                                        <th>Montant total</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody class="table-border-bottom-0">
                                    <% for (Sale sale : (ArrayList<Sale>) request.getAttribute("sales")) { %>
                                    <tr>
                                        <td><strong><%= sale.getIdSale() %></strong></td>
                                        <td><%= sale.getIdUser() %></td>
                                        <td><%= sale.getSaleDate() %></td>
                                        <td><%= sale.getTotalAmount() %></td>
                                        <td>
                                            <div class="dropdown">
                                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                                    <i class="bx bx-dots-vertical-rounded"></i>
                                                </button>
                                                <div class="dropdown-menu">
                                                    <a class="dropdown-item" href="sale-details?idSale=<%= sale.getIdSale() %>">
                                                        <i class="bx bx-book-content me-1"></i> Détails
                                                    </a>
                                                    <% if (connected) { %>
                                                    <a class="dropdown-item" href="form-sale?action=update&id=<%= sale.getIdSale() %>">
                                                        <i class="bx bx-edit-alt me-1"></i> Modifier
                                                    </a>
                                                    <a class="dropdown-item" href="sales?action=delete&id=<%= sale.getIdSale() %>">
                                                        <i class="bx bx-trash me-1"></i> Supprimer
                                                    </a>
                                                    <% } %>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!--/ Basic Bootstrap Table -->
                </div>
                <!-- / Content -->
            </div>
            <!-- / Content wrapper -->
        </div>
        <!-- / Layout container -->
    </div>
</div>
<!-- / Layout wrapper -->
<%@include file="footer.jsp" %>
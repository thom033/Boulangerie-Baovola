<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.Recipe, dao.Category, java.util.ArrayList, util.SessionUtils" %>
<% boolean connected = SessionUtils.isUserConnected(request); %>
<%@include file="header.jsp"%>
<div class="layout-wrapper layout-content-navbar">
    <div class="layout-container">
        <%@include file="vertical-menu.jsp"%>
        <div class="layout-page">
            <nav class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme" id="layout-navbar">
                <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
                    <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                        <i class="bx bx-menu bx-sm"></i>
                    </a>
                </div>
                <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
                    <ul class="navbar-nav flex-row align-items-center ms-auto">
                        <%@ include file="user.jsp" %>
                    </ul>
                </div>
            </nav>
            <div class="content-wrapper">
                <div class="container-xxl flex-grow-1 container-p-y">
                    <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Formulaire /</span> Vente</h4>
                    <div class="row">
                        <div class="col-lg-6 mx-auto">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">Vente</h5>
                                </div>
                                <div class="card-body">
                                    <form action="sales" method="POST">
                                        <div class="mb-3">
                                            <label for="idUser" class="form-label">ID Utilisateur</label>
                                            <input type="number" id="idUser" name="idUser" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="saleDate" class="form-label">Date de vente</label>
                                            <input type="date" id="saleDate" name="saleDate" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="totalAmount" class="form-label">Montant total</label>
                                            <input type="number" step="0.01" id="totalAmount" name="totalAmount" class="form-control" required>
                                        </div>
                                        <button type="button" class="btn btn-primary" onclick="addProductForm()">Ajouter un produit</button>
                                        <div id="productsContainer" style="margin-top: 20px;"></div>
                                        <button type="submit" class="btn btn-success">Ajouter</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    let productCount = 0;

    function addProductForm() {
        productCount++;
        const container = document.getElementById('productsContainer');
        const productForm = document.createElement('div');
        productForm.className = 'product-form mb-3';
        productForm.innerHTML = `
            <h5>Produit ${productCount}</h5>
            <div class="mb-3">
                <label for="idRecipe${productCount}" class="form-label">ID Recipe</label>
                <select name="idRecipe${productCount}" id="idRecipe${productCount}" class="form-select" required>
                    <% 
                    ArrayList<Recipe> recipes = (ArrayList<Recipe>) request.getAttribute("recipes");
                    for (Recipe recipe : recipes) { 
                    %>
                    <option value="<%= recipe.getId() %>"><%= recipe.getTitle() %></option>
                    <% } %>
                </select>
            </div>
            <div class="mb-3">
                <label for="qtt${productCount}" class="form-label">Quantité</label>
                <input type="number" id="qtt${productCount}" name="qtt${productCount}" class="form-control">
            </div>
            <button type="button" class="btn btn-secondary" onclick="removeProductForm(this)">Supprimer</button>
        `;
        container.appendChild(productForm);
    }

    function removeProductForm(button) {
        button.parentElement.remove();
    }
</script>

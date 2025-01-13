<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.Ingredient, dao.Category, java.util.ArrayList, util.SessionUtils" %>
<%
    boolean connected = SessionUtils.isUserConnected(request);
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vente</title>
    <!-- Add Bootstrap CSS and custom styles -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fc;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 90%;
            margin: 0 auto;
            padding-top: 50px;
        }

        .fw-bold {
            font-weight: bold;
        }

        .form-label {
            font-size: 1.1rem;
            color: #333;
        }

        .form-control {
            padding: 10px;
            font-size: 1rem;
            margin-top: 5px;
        }

        .btn-primary {
            background-color: #007bff;
            border: none;
            color: white;
            padding: 10px 20px;
            font-size: 1rem;
            border-radius: 5px;
            margin-top: 10px;
            transition: background-color 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .card {
            margin-top: 20px;
            border-radius: 8px;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #007bff;
            color: white;
            padding: 15px;
            font-size: 1.25rem;
        }

        .table th {
            background-color: #007bff;
            color: white;
        }

        .table-responsive {
            overflow-x: auto;
        }

        .dropdown-menu {
            min-width: 180px;
        }

        .alert-danger {
            margin-top: 10px;
            padding: 15px;
            background-color: #f8d7da;
            color: #721c24;
            border-radius: 5px;
        }

        .alert-dismissible .btn-close {
            background: transparent;
            border: none;
            color: #721c24;
        }

        .dropdown-toggle::after {
            display: none !important;
        }

        .dropdown-item i {
            margin-right: 8px;
        }

        .btn-success {
            background-color: #28a745;
            color: white;
            font-size: 1rem;
            padding: 8px 16px;
            border-radius: 5px;
        }

        .btn-success:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="container">
        <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Gotta taste /</span> Vente</h4>

        <!-- Category filter form -->
        <form method="post" action="vente">
            <div class="mb-3">
                <label class="form-label" for="categoryId">Filtrer par catégorie</label>
                <select name="categoryName" id="categoryName" class="form-control">
                    <option value="">-- Sélectionner une catégorie --</option>
                    <% 
                        ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
                        if (categories != null) {
                            for (Category category : categories) {
                    %>
                    <option value="<%= category.getName() %>"><%= category.getName() %></option>
                    <% 
                            }
                        }
                    %>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label" for="isNature">Filtrer par Nature</label>
                <input type="checkbox" name="isNature" id="isNature" value="true" class="form-check-input">
                <label class="form-check-label" for="isNature">Nature</label>
            </div>
            <button type="submit" class="btn btn-primary">Filtrer</button>
        </form>

        <!-- Sales List -->
        <div class="card">
            <h5 class="card-header">Liste des ventes</h5>
            <div class="card-body">
                <% if (connected) { %>
                    <div class="mb-3">
                        <a href="form-ingredient" type="button" class="btn btn-success">Ajouter</a>
                    </div>
                <% } %>
                <% if(errorMessage != null) { %>
                    <div class="alert alert-danger alert-dismissible mb-0" role="alert">
                        <%= errorMessage %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                <% } %>
            </div>
            <div class="table-responsive text-nowrap">
                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>id vente</th>
                            <th>Date de la vente</th>
                            <th>Nom de l'user</th>
                            <th>Nom du produit</th>
                            <th>Quantité</th>
                            <th>Unite price</th>
                            <th>Total</th>
                            <% if (connected) { %>
                                <th>Actions</th>
                            <% } %>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            ArrayList<String[]> list = (ArrayList<String[]>) request.getAttribute("filteredSales");
                            if (list == null) {
                                list = (ArrayList<String[]>) request.getAttribute("liste_ventes");
                            }
                            if (list != null) {
                                for (String[] item : list) { 
                                    String id = item[0];          
                                    String daty = item[1];        
                                    String nom_user = item[2];    
                                    String nom_produit = item[3]; 
                                    String quantite = item[4];    
                                    String unit_price = item[5];  
                                    String total = item[6];      
                        %>
                        <tr>
                            <td><strong><%= id %></strong></td>
                            <td><%= daty %></td>              
                            <td><%= nom_user %></td>          
                            <td><%= nom_produit %></td>        
                            <td><%= quantite %></td>         
                            <td><%= unit_price %> Ar</td>     
                            <td><%= total %> Ar</td>         
                            
                            <% if (connected) { %>
                            <td>
                                <div class="dropdown">
                                    <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                        <i class="bx bx-dots-vertical-rounded"></i>
                                    </button>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item" href="form-ingredient?action=update&id=<%= id %>">
                                            <i class="bx bx-edit-alt me-1"></i> Modifier
                                        </a>
                                        <a class="dropdown-item" href="ingredient?action=delete&id=<%= id %>">
                                            <i class="bx bx-trash me-1"></i> Supprimer
                                        </a>
                                    </div>
                                </div>
                            </td>
                            <% } %>
                        </tr>
                        <% 
                                }
                            }
                        %>
                    </tbody>
                </table>
                <a href="formvente">Ajouter Vente</a>
            </div>
        </div>
        <!--/ Sales List -->
    </div>

    <%@ include file="footer.jsp" %>

    <!-- Add Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String activeMenuItem = (String) request.getAttribute("activeMenuItem");
%>

<aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
    <!-- App brand -->
    <div class="app-brand demo">
        <a href="recipe" class="app-brand-link">
                      <span class="app-brand-logo demo">
                        <img width="25" src="assets/img/favicon/mofo.png" alt="Gotta taste logo">
                      </span>
            <span class="app-brand-text demo menu-text fw-bolder ms-2">Mofoko</span>
        </a>

        <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto d-block d-xl-none">
            <i class="bx bx-chevron-left bx-sm align-middle"></i>
        </a>
    </div>
    <!-- / App brand -->

    <div class="menu-inner-shadow"></div>

    <ul class="menu-inner py-1">
        <!-- Recipe -->
        <li class="menu-item">
            <a href="recipe" class="menu-link">
                <i class="menu-icon tf-icons bx bx-book"></i>
                <div data-i18n="Recipies">Recettes</div>
            </a>
        </li>

        <!-- Category -->
        <li class="menu-item">
            <a href="category" class="menu-link">
                <i class="menu-icon tf-icons bx bx-category"></i>
                <div data-i18n="Categories">Catégories</div>
            </a>
        </li>

        <!-- Ingredient -->
        <li class="menu-item">
            <a href="ingredient" class="menu-link">
                <i class="menu-icon tf-icons bx bx-dish"></i>
                <div data-i18n="Ingredients">Ingrédients</div>
            </a>
        </li>

        <%-- <!-- Step -->
        <li class="menu-item">
            <a href="step" class="menu-link">
                <i class="menu-icon tf-icons bx bx-book-open"></i>
                <div data-i18n="Steps">Etapes</div>
            </a>
        </li>

        <!-- Review -->
        <li class="menu-item">
            <a href="review" class="menu-link">
                <i class="menu-icon tf-icons bx bxs-star-half"></i>
                <div data-i18n="Reviews">Retours</div>
            </a>
        </li> --%>

        <!-- Stock -->
        <li class="menu-item">
            <a href="form-add-ingredient.jsp" class="menu-link">
                <i class="menu-icon tf-icons bx bx-category"></i>
                <div data-i18n="Reviews">Stock Ingredient Form</div>
            </a>
        </li>
        <li class="menu-item">
            <a href="form-add-ingredient.jsp" class="menu-link">
                <i class="menu-icon tf-icons bx bx-category"></i>
                <div data-i18n="Reviews">Stock Produit Form</div>
            </a>
        </li>
        <li class="menu-item">
            <a href="sales" class="menu-link">
                <i class="menu-icon tf-icons bx bx-category"></i>
                <div data-i18n="Reviews">Sales</div>
            </a>
        </li>
    </ul>
</aside>
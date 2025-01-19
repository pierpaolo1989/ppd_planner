package com.soa.ppd_planner.layout;

import com.soa.ppd_planner.view.MainView;
import com.soa.ppd_planner.view.PortfolioView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        // Header
        H1 title = new H1("PPD Planner");
        Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));

        HorizontalLayout header = new HorizontalLayout(title, logoutButton);
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle().set("background-color", "#f3f3f3");

        addToNavbar(header);

        // Sidebar
        RouterLink homeLink = new RouterLink("Home", MainView.class);
        RouterLink portfolioLink = new RouterLink("Portfolio", PortfolioView.class);
        RouterLink coinsLink = new RouterLink("Coins", MainView.class);

        VerticalLayout sidebar = new VerticalLayout(homeLink, coinsLink, portfolioLink);
        sidebar.setPadding(true);
        sidebar.setSpacing(true);
        sidebar.setWidth("100%");
        sidebar.setHeightFull();
        sidebar.getStyle().set("background-color", "#e0e0e0");

        addToDrawer(sidebar);
    }
}
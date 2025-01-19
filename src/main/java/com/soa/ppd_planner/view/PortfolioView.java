package com.soa.ppd_planner.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

@Route("portfolio")
@PermitAll
public class PortfolioView extends Div {

    public PortfolioView () {
        // build layout
        // Layout principale
        HorizontalLayout header = createHeader();
        VerticalLayout sidebar = createSidebar();
        VerticalLayout mainContent = createMainContent();

        // Layout globale
        HorizontalLayout layout = new HorizontalLayout(sidebar, mainContent);
        layout.setSizeFull();
        layout.setFlexGrow(1, mainContent);
        layout.expand(mainContent);

        add(header, layout);
    }

    private HorizontalLayout createHeader() {
        H1 title = new H1("PPD Planner");
        HorizontalLayout header = new HorizontalLayout(title);
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle().set("background-color", "#f3f3f3");
        return header;
    }

    private VerticalLayout createSidebar() {
        RouterLink homeLink = new RouterLink("Home", MainView.class);
        RouterLink otherLink = new RouterLink("Portfolio", PortfolioView.class);

        VerticalLayout sidebar = new VerticalLayout(homeLink, otherLink);
        sidebar.setWidth("200px");
        sidebar.setHeightFull();
        sidebar.setPadding(true);
        sidebar.getStyle().set("background-color", "#e0e0e0");
        return sidebar;
    }

    private VerticalLayout createMainContent() {
        // Imposta i componenti della vista principale
        Text text = new Text("Hello @Route");
        VerticalLayout mainContent = new VerticalLayout(text);
        mainContent.setSizeFull();
        mainContent.setPadding(true);
        return mainContent;
    }

}

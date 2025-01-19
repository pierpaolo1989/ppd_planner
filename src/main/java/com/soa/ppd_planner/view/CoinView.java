package com.soa.ppd_planner.view;

import com.soa.ppd_planner.dao.CoinRepository;
import com.soa.ppd_planner.editor.CoinEditor;
import com.soa.ppd_planner.model.Coin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.util.StringUtils;

@Route("coins")
@PageTitle("Coins")
@PermitAll
public class CoinView extends Div {

    private final CoinRepository repo;

    private final CoinEditor editor;

    final Grid<Coin> grid;

    final TextField filter;

    private final Button addNewBtn;

    public CoinView(CoinRepository repo, CoinEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Coin.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New coin", VaadinIcon.PLUS.create());

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

        // Initialize listing
        listCoins(null);
    }

    // tag::listCoins[]
    void listCoins(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(repo.findByCountry(filterText));
        } else {
            grid.setItems(repo.findAll());
        }
    }
    // end::listCoins[]

    private HorizontalLayout createHeader() {
        H1 title = new H1("PPD Planner");
        // Pulsante di logout
        Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> {
            // Logica per il logout (ad esempio, reindirizzamento alla pagina di login)
            getUI().ifPresent(ui -> ui.navigate("login"));
        });
        HorizontalLayout header = new HorizontalLayout(title, logoutButton);
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle().set("background-color", "#f3f3f3");
        return header;
    }

    private VerticalLayout createSidebar() {
        RouterLink homeLink = new RouterLink("Home", MainView.class);
        RouterLink otherLink = new RouterLink("Portfolio", PortfolioView.class);
        RouterLink coinsLink = new RouterLink("Coins", MainView.class);

        VerticalLayout sidebar = new VerticalLayout(homeLink, otherLink, coinsLink);
        sidebar.setWidth("200px");
        sidebar.setPadding(true);
        sidebar.getStyle().set("background-color", "#e0e0e0");
        return sidebar;
    }

    private VerticalLayout createMainContent() {
        // Imposta i componenti della vista principale
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainContent = new VerticalLayout(actions, grid, editor);
        mainContent.setSizeFull();
        mainContent.setPadding(true);

        grid.setHeight("300px");
        grid.setColumns("id", "country", "value", "year", "number");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by country");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCoins(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCoins(filter.getValue());
        });

        listCoins(null);

        return mainContent;
    }


}
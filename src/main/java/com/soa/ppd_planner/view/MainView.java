package com.soa.ppd_planner.view;

import com.soa.ppd_planner.dao.CoinRepository;
import com.soa.ppd_planner.editor.CoinEditor;
import com.soa.ppd_planner.layout.MainLayout;
import com.soa.ppd_planner.model.Coin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.util.StringUtils;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class MainView extends VerticalLayout  {

    private final CoinRepository repo;

    private final CoinEditor editor;

    final Grid<Coin> grid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView(CoinRepository repo, CoinEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Coin.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New coin", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

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

}
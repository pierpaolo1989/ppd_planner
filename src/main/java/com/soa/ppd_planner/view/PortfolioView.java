package com.soa.ppd_planner.view;

import com.soa.ppd_planner.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@Route(value = "portfolio", layout = MainLayout.class)
@PermitAll
public class PortfolioView extends VerticalLayout {

    private final List<Asset> assets = new ArrayList<>();
    private final Grid<Asset> grid = new Grid<>(Asset.class);
    private Div chartContainer;

    public PortfolioView() {
        // Popola la tabella con alcuni dati di esempio
        assets.add(new Asset("Stocks", 50));
        assets.add(new Asset("Bonds", 20));
        assets.add(new Asset("Real Estate", 15));
        assets.add(new Asset("Commodities", 10));
        assets.add(new Asset("Cash", 5));

        // Configura la griglia
        configureGrid();

        // Aggiungi la griglia
        add(new H1("Portfolio Allocation Table"));
        add(grid);

        // Crea il contenitore del grafico
        chartContainer = new Div();
        chartContainer.setId("chart-container");
        chartContainer.getElement().setProperty("innerHTML",
                "<canvas id='chart' width='600' height='400'></canvas>");
        add(chartContainer);

        // Inizializza il grafico
        updateChart();

        // Aggiungi un pulsante per il salvataggio (opzionale)
        Button saveButton = new Button("Save Changes", e -> updateChart());
        add(saveButton);
    }

    private void configureGrid() {
        // Configura le colonne della griglia
        grid.setItems(assets);
        grid.addColumn(Asset::getName).setHeader("Asset Class").setSortable(true);
        Grid.Column<Asset> valueColumn = grid.addColumn(Asset::getValue).setHeader("Value");

        // Aggiungi l'editor per modificare i valori
        Editor<Asset> editor = grid.getEditor();
        Binder<Asset> binder = new Binder<>(Asset.class);
        editor.setBinder(binder);

        TextField valueField = new TextField();
        binder.forField(valueField).bind("value");
        valueColumn.setEditorComponent(valueField);

        grid.addItemDoubleClickListener(event -> editor.editItem(event.getItem()));
        grid.getElement().addEventListener("keyup", e -> editor.save())
                .setFilter("event.key === 'Enter'");
    }

    private void updateChart() {
        // Genera i dati aggiornati per il grafico
        String labels = "[";
        String data = "[";

        for (Asset asset : assets) {
            labels += "'" + asset.getName() + "',";
            data += asset.getValue() + ",";
        }

        labels = labels.substring(0, labels.length() - 1) + "]";
        data = data.substring(0, data.length() - 1) + "]";

        // Aggiorna il grafico
        chartContainer.getElement().executeJs(
                "const script = document.createElement('script');" +
                        "script.src = 'https://cdn.jsdelivr.net/npm/chart.js';" +
                        "script.onload = () => {" +
                        "const ctx = document.getElementById('chart').getContext('2d');" +
                        "if (window.myChart) { window.myChart.destroy(); }" + // Distruggi il grafico precedente
                        "window.myChart = new Chart(ctx, {" +
                        "    type: 'pie'," +
                        "    data: {" +
                        "        labels: " + labels + "," +
                        "        datasets: [{" +
                        "            data: " + data + "," +
                        "            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF']" +
                        "        }]" +
                        "    }" +
                        "});" +
                        "};" +
                        "document.head.appendChild(script);"
        );
    }

    // Classe interna per rappresentare un asset
    public static class Asset {
        private String name;
        private double value;

        public Asset(String name, double value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
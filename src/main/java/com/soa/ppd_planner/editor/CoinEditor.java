package com.soa.ppd_planner.editor;


import com.soa.ppd_planner.dao.CoinRepository;
import com.soa.ppd_planner.model.Coin;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class CoinEditor extends VerticalLayout implements KeyNotifier {

    private final CoinRepository repository;

    /**
     * The currently edited customer
     */
    private Coin coin;

    /* Fields to edit properties in Customer entity */
    TextField country = new TextField("Country");
    TextField value = new TextField("Value");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button edit = new Button("Edit", VaadinIcon.EDIT.create());
    HorizontalLayout actions = new HorizontalLayout(save, edit, delete);

    Binder<Coin> binder = new Binder<>(Coin.class);
    private ChangeHandler changeHandler;

    @Autowired
    public CoinEditor(CoinRepository repository) {

        this.repository = repository;

        add(country, value, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        edit.addThemeVariants(ButtonVariant.LUMO_ICON);

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        edit.addClickListener(e -> editCoin(coin));
        setVisible(false);
    }

    void delete() {
        repository.delete(coin);
        changeHandler.onChange();
    }

    void save() {
        repository.save(coin);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editCoin(Coin coin) {
        if (coin == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = coin.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            // In a more complex app, you might want to load
            // the entity/DTO with lazy loaded relations for editing
            coin = repository.findById(coin.getId()).get();
        }
        else {
            coin = coin;
        }
        edit.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(coin);

        setVisible(true);

        // Focus first name initially
        country.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

}
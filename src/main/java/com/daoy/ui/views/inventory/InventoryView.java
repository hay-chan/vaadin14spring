package com.daoy.ui.views.inventory;

import com.daoy.backend.data.Product;
import com.daoy.backend.service.DataService;
import com.daoy.ui.MainView;
import com.daoy.ui.views.ChekovParam;
import com.daoy.ui.views.ChekovView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link SampleCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "Inventory", layout = MainView.class)
//@RouteAlias(value = "", layout = MainLayout.class)
public class InventoryView extends ChekovView<InventoryView.InventoryParam, InventoryViewLogic>
{
    @Getter @Setter
    public class InventoryParam implements ChekovParam
    {
        private int id;
    }
    
    public static final String VIEW_NAME = "Inventory";
    private final ProductGrid grid;
    private final ProductForm form;
    private TextField filter;

    private Button newProduct;

    private final ProductDataProvider dataProvider = new ProductDataProvider();

    public InventoryView( ) {
        setViewLogic( new InventoryViewLogic( this ) );
        setSizeFull();
        final HorizontalLayout topLayout = createTopBar();

        grid = new ProductGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> getViewLogic().rowSelected(event.getValue()));

        form = new ProductForm(getViewLogic());
        form.setCategories( DataService .get().getAllCategories());

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
        add(form);

        getViewLogic().init();
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter name, availability or category");
        // Apply the filter to grid's data provider. TextField value is never
        // null
        filter.addValueChangeListener(
                event -> dataProvider.setFilter(event.getValue()));
        filter.addFocusShortcut( Key.KEY_F, KeyModifier.CONTROL);

        newProduct = new Button("New product");
        newProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newProduct.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newProduct.addClickListener(click -> getViewLogic().newProduct());
        // CTRL+N will create a new window which is unavoidable
        newProduct.addClickShortcut( Key.KEY_N, KeyModifier.ALT);

        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newProduct);
        topLayout.setVerticalComponentAlignment( FlexComponent.Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }

    public void setNewProductEnabled(boolean enabled) {
        newProduct.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow( Product row) {
        grid.getSelectionModel().select(row);
    }

    public Product getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateProduct(Product product) {
        dataProvider.save(product);
    }

    public void removeProduct(Product product) {
        dataProvider.delete(product);
    }

    public void editProduct(Product product) {
        showForm(product != null);
        form.editProduct(product);
    }

    public void showForm(boolean show) {
        form.setVisible(show);
        form.setEnabled(show);
    }
}

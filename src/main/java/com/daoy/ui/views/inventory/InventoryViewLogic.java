package com.daoy.ui.views.inventory;

import com.daoy.app.authentication.AccessControl;
import com.daoy.app.authentication.AccessControlFactory;
import com.daoy.backend.data.Product;
import com.daoy.backend.service.DataService;
import com.daoy.ui.views.ChekovView;
import com.daoy.ui.views.ChekovViewLogic;
import com.vaadin.flow.component.UI;
import org.springframework.stereotype.Service;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class InventoryViewLogic extends ChekovViewLogic<InventoryView.InventoryParam>
{
    private InventoryView view;
    
    public InventoryViewLogic( InventoryView chekovView )
    {
        super( chekovView );
        this.view = (InventoryView ) getChekovView();
    }
    
    public void init( ) {
        editProduct(null);
        // Hide and disable if not admin
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole( AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewProductEnabled(false);
        }
    }

    public void cancelProduct() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        UI.getCurrent().navigate(InventoryView.class, fragmentParameter);
    }

    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newProduct();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    final int pid = Integer.parseInt(productId);
                    final Product product = findProduct(pid);
                    view.selectRow(product);
                } catch (final NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private Product findProduct(int productId) {
        return DataService.get().getProductById(productId);
    }

    public void saveProduct(Product product) {
        final boolean newProduct = product.isNewProduct();
        view.clearSelection();
        view.updateProduct(product);
        setFragmentParameter("");
        view.showSaveNotification(product.getProductName()
                + (newProduct ? " created" : " updated"));
    }

    public void deleteProduct(Product product) {
        view.clearSelection();
        view.removeProduct(product);
        setFragmentParameter("");
        view.showSaveNotification(product.getProductName() + " removed");
    }

    public void editProduct(Product product) {
        if (product == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(product.getId() + "");
        }
        view.editProduct(product);
    }

    public void newProduct() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editProduct(new Product());
    }

    public void rowSelected(Product product) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editProduct(product);
        }
    }
    
    @Override
    public void enter( InventoryView.InventoryParam inventoryParam )
    {
        if ( inventoryParam != null )
        {
            final Product product = findProduct( inventoryParam.getId() );
            view.selectRow( product );
        }
        else
        {
            view.showForm( false );
        }
    }
}

package com.daoy.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import com.daoy.ui.views.orderedit.OrderItemEditor;

public class DeleteEvent extends ComponentEvent<OrderItemEditor> {
	public DeleteEvent(OrderItemEditor component) {
		super(component, false);
	}
}
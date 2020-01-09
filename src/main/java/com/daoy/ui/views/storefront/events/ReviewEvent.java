package com.daoy.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import com.daoy.ui.views.orderedit.OrderEditor;

public class ReviewEvent extends ComponentEvent<OrderEditor> {

	public ReviewEvent(OrderEditor component) {
		super(component, false);
	}
}
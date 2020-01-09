package com.daoy.ui.utils.converters;

import com.vaadin.flow.templatemodel.ModelEncoder;
import com.daoy.ui.dataproviders.DataProviderUtil;
import com.daoy.ui.utils.FormattingUtils;

public class CurrencyFormatter implements ModelEncoder<Integer, String> {

	@Override
	public String encode(Integer modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue, FormattingUtils::formatAsCurrency);
	}

	@Override
	public Integer decode(String presentationValue) {
		throw new UnsupportedOperationException();
	}
}

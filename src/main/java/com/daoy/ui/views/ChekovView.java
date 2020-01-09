package com.daoy.ui.views;

import com.google.gson.Gson;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.WildcardParameter;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;

public abstract class ChekovView<PARAM extends ChekovParam, VIEW_LOGIC extends ChekovViewLogic<PARAM>> extends HorizontalLayout implements HasUrlParameter<String>
{
    @Getter
    @Setter
    private VIEW_LOGIC viewLogic;
    
    @Override
    public void setParameter( BeforeEvent beforeEvent, @WildcardParameter String paramString )
    {
        beforeEvent.getUI().getRouter();
        beforeEvent.getLayouts();
        
        Gson g = new Gson();
        PARAM param = g.fromJson( paramString, getParamClass() );
        
        viewLogic.enter( param );
    }
    
    private Class<PARAM> getParamClass()
    {
        Class<PARAM> tClass = ( Class<PARAM> ) ( ( ParameterizedType ) getClass().getGenericSuperclass() ).getActualTypeArguments()[0];
        return tClass;
    }
}

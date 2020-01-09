package com.daoy.ui.views;

import lombok.Getter;

import java.io.Serializable;

public abstract class ChekovViewLogic<PARAM extends ChekovParam> implements Serializable
{
    @Getter
    final ChekovView chekovView;

    public ChekovViewLogic( final ChekovView chekovView )
    {
        this.chekovView = chekovView;
    }
    
    abstract public void enter( PARAM param );
}

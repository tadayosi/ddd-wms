package com.googlecode.dddwms.domain.service;

public class Item {

    private final long _id;
    
    public Item(long id) {
        // TODO Auto-generated constructor stub
        _id = id;
    }

    public long id() {
        // TODO Auto-generated method stub
        return _id;
    }
    
    @Override
    public boolean equals(Object arg0) {
        // TODO Auto-generated method stub
        if (!(arg0 instanceof Item)){
            return false;
        }
        Item another = (Item)arg0;        
        return another.id() == _id;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return Long.valueOf(_id).hashCode();
        //return super.hashCode();
    }

}

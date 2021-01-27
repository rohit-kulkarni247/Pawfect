package com.example.petlify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Pro implements Map<String, Object> {
    String name,add,city,country,pincode,contactNo;

    public Pro(String name, String add, String city, String country, String pincode, String contactNo) {
        this.name = name;
        this.add = add;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
        this.contactNo = contactNo;
    }
    public  Pro(){

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(@Nullable Object o) {
        return false;
    }

    @Override
    public boolean containsValue(@Nullable Object o) {
        return false;
    }

    @Nullable
    @Override
    public Object get(@Nullable Object o) {
        return null;
    }

    @Nullable
    @Override
    public Object put(String s, Object o) {
        return null;
    }

    @Nullable
    @Override
    public Object remove(@Nullable Object o) {
        return null;
    }

    @Override
    public void putAll(@NonNull Map<? extends String, ?> map) {

    }

    @Override
    public void clear() {

    }

    @NonNull
    @Override
    public Set<String> keySet() {
        return null;
    }

    @NonNull
    @Override
    public Collection<Object> values() {
        return null;
    }

    @NonNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}

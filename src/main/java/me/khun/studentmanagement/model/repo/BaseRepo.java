package me.khun.studentmanagement.model.repo;

import java.util.*;

public interface BaseRepo<T, ID> {

    public T create(T entity);

    public boolean update(T entity);

    public boolean deleteById(ID id);

    public T findById(ID id);

    public List<T> findAll();

    public long getCount();

}
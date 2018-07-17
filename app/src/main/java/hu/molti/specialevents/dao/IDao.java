package hu.molti.specialevents.dao;

import java.util.List;

public interface IDao<Entity> {
    List<Entity> getAll();
    void insert(Entity entity);
    void delete(Entity entity);
}

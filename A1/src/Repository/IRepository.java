package Repository;

import Model.IObject;

public interface IRepository {
    public void add(IObject object);
    public void delete(IObject object);
    public IObject[] getAll();
    public Integer getSize();
}

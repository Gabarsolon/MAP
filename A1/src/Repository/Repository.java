package Repository;

import Model.IObject;

public class Repository implements IRepository{
    private IObject[] objects;
    Integer size;
    Integer maxsize;
    public Repository(Integer maxsize){
        this.size = 0;
        this.maxsize = maxsize;
        this.objects = new IObject[maxsize];
    }
    public void add(IObject object){
        try{
            this.objects[size] = object;
            size++;
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public void delete(IObject object){
        try{
            IObject[] newObjects = new IObject[this.maxsize];
            int index = 0;
            for(int i = 0;i<size;i++){
                if(this.objects[i].toString().compareTo(object.toString()) != 0){
                    newObjects[index] = this.objects[i];
                    index++;
                }
            }
            this.objects = newObjects;
            this.size = index;
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public Integer getSize(){
        return this.size;
    }
    public IObject[] getAll(){
        return this.objects;
    }
}
